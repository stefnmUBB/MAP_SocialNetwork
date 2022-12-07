package sn.socialnetwork.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import sn.socialnetwork.domain.Friendship;
import sn.socialnetwork.domain.User;
import sn.socialnetwork.repo.EntityAlreadyExistsException;
import sn.socialnetwork.service.EntityIdNotFoundException;
import sn.socialnetwork.utils.Constants;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserViewController extends SocialNetworkController {
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        nameTextField.setText(user.getFirstName()+" "+user.getLastName());
        emailTextField.setText(user.getEmail());
        ageTextField.setText(user.getAge().toString());

        refreshFriends();
        refreshRequests();
    }

    void refreshFriends(){
        friends.clear();
        var friendsof = getNetwork().getFriendsOf(user);
        //System.out.println(friendsof.size());
        friends.addAll(friendsof);
    }

    void refreshRequests() {
        requests.clear();
        var reqs = getNetwork().getFriendRequestsOf(user);
        requests.addAll(reqs);
    }

    @FXML
    TabPane tabControl;

    @FXML
    TextField nameTextField;

    @FXML
    TextField emailTextField;

    @FXML
    TextField ageTextField;

    @FXML
    protected void initialize() {
        tabControl.getTabs().forEach(tab->{
            Platform.runLater(() -> {
                Parent tabContainer = tab.getGraphic().getParent().getParent();
                tabContainer.setRotate(90);
                tabContainer.setTranslateY(-100);
            });
        });

        friendNameColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName"));
        friendSurnameColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName"));
        friendAgeColumn.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("age"));
        friendSinceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> p) {
                User u = p.getValue();

                LocalDateTime date = StreamSupport.stream(getNetwork().getAllFriendships().spliterator(),false)
                        .filter(f->f.containsUser(user.getId()) && f.containsUser(u.getId()))
                        .map(Friendship::getFriendsFrom)
                        .findFirst().orElse(null);
                if(date==null)
                    return new SimpleStringProperty("");
                return new SimpleStringProperty(date.format(Constants.DATE_TIME_FORMATTER));
            }
        });

        friendsTable.setItems(friends);


        discNameColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName"));
        discSurnameColumn.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName"));

        discTable.setItems(discUsers);

        searchTextField.textProperty().addListener(o->discoverFilter());

        fshipUserNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Friendship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Friendship, String> p) {
                Friendship f = p.getValue();
                User friend = getNetwork().getUserById(f.getTheOtherOne(user.getId()));
                return new SimpleStringProperty(friend.getLastName()+" "+friend.getFirstName());
            }
        });

        fshipSentColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Friendship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Friendship, String> p) {
                Friendship f = p.getValue();
                return new SimpleStringProperty(f.getFriendsFrom().format(Constants.DATE_TIME_FORMATTER));
            }
        });

        fshipStatusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Friendship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Friendship, String> p) {
                Friendship f = p.getValue();
                if(f.isSender(user.getId())) {
                    return new SimpleStringProperty("Outcome");
                }
                return new SimpleStringProperty("Income");
            }
        });

        fshipsTable.setItems(requests);
    }


    @FXML
    TableColumn<User, String> friendNameColumn;
    @FXML
    TableColumn<User, String> friendSurnameColumn;
    @FXML
    TableColumn<User, Integer> friendAgeColumn;
    @FXML
    TableColumn<User, String> friendSinceColumn;
    @FXML
    TableView<User> friendsTable;

    ObservableList<User> friends = FXCollections.observableArrayList();

    @FXML
    TextField searchTextField;

    @FXML
    TableView<User> discTable;

    @FXML
    TableColumn<User, String> discSurnameColumn;

    @FXML
    TableColumn<User, String> discNameColumn;

    ObservableList<User> discUsers = FXCollections.observableArrayList();

    void discoverFilter() {
        discAddFriendButton.setDisable(true);
        String[] keys = Arrays.stream(searchTextField.getText().split(" "))
                .map(String::toLowerCase).toArray(String[]::new);
        Predicate<User> pNotEmpty = u-> keys.length>1 || (keys.length==1 && keys[0].length()>0);
        Predicate<User> pName =u-> Arrays.stream(keys)
                .anyMatch(s->u.getFirstName().toLowerCase().contains(s));
        Predicate<User> pSurname =u-> Arrays.stream(keys)
                .anyMatch(s->u.getLastName().toLowerCase().contains(s));
        discUsers.setAll(StreamSupport.stream(getNetwork().getAllUsers().spliterator(),false)
                .filter(pNotEmpty.and(pName.or(pSurname)))
                .collect(Collectors.toList()));
    }

    @FXML
    Button discAddFriendButton;
    private User discSelectedUser;

    public void discTableMouseClicked() {
        discSelectedUser = discTable.getSelectionModel().getSelectedItem();
        if(discSelectedUser==null) {
            discAddFriendButton.setDisable(true);
            return;
        }
        discAddFriendButton.setDisable(false);
    }

    public void discAddFriendButtonClicked() {
        Friendship f = new Friendship(user.getId(), discSelectedUser.getId(),
                LocalDateTime.now());
        f.setSender(user.getId());
        try {
            getNetwork().addFriendship(f);
            refreshFriends();
            refreshRequests();
        } catch (EntityAlreadyExistsException e) {
            showErrorBox("Friendship already exists");
        }
    }

    @FXML
    TableView<Friendship> fshipsTable;
    @FXML
    TableColumn<Friendship, String> fshipUserNameColumn;
    @FXML
    TableColumn<Friendship, String> fshipSentColumn;
    @FXML
    TableColumn<Friendship, String> fshipStatusColumn;

    ObservableList<Friendship> requests = FXCollections.observableArrayList();

    @FXML
    Button removeFriendshipButton;

    @FXML
    Button acceptRequestButton;

    Friendship selectedFriendship = null;

    @FXML
    public void acceptRequestButtonClicked() {
        if(selectedFriendship==null) return;

        selectedFriendship.setPending(false); // accepted
        getNetwork().updateFriendship(selectedFriendship);

        refreshFriends();
        refreshRequests();
    }

    @FXML
    public void removeFriendshipButtonClicked() {
        if(selectedFriendship==null) return;
        try {
            getNetwork().removeFriendship(selectedFriendship.getId());
        } catch (EntityIdNotFoundException e) {
            showErrorBox("Friendship not found");
            return;
        }
        refreshFriends();
        refreshRequests();
    }

    @FXML
    public void fshipsTableClicked() {
        selectedFriendship = fshipsTable.getSelectionModel().getSelectedItem();
        acceptRequestButton.setDisable(true);
        removeFriendshipButton.setDisable(true);
        if(selectedFriendship==null) {
            return;
        }
        if(!selectedFriendship.isSender(user.getId())) {
            acceptRequestButton.setDisable(false);
        }
        removeFriendshipButton.setDisable(false);
    }

    Friendship selectedExistentFriendship = null;

    @FXML
    Button removeFriendButton;

    @FXML
    public void friendsTableClicked() {
        var selectedFriend = friendsTable.getSelectionModel().getSelectedItem();
        System.out.println("Friend = "+selectedFriend);
        if(selectedFriend == null) return;

        selectedExistentFriendship = StreamSupport.stream(getNetwork().getAllFriendships().spliterator(),false)
                .filter(f->f.containsUser(user.getId()) && f.containsUser(selectedFriend.getId()))
                .findFirst().orElse(null);

        System.out.println("Friendship = "+selectedExistentFriendship);

        removeFriendButton.setDisable(selectedExistentFriendship==null);
    }

    @FXML
    public void removeFriendButtonClicked() {
        if(selectedExistentFriendship==null)
            return;
        try {
            getNetwork().removeFriendship(selectedExistentFriendship.getId());
        } catch (EntityIdNotFoundException e) {
            showErrorBox("Friendship does not exist");
            return;
        }
        refreshFriends();
        refreshRequests();
    }

    @FXML
    public void logoutButtonClicked(){
        Stage loginViewStage = createStage("login-view.fxml",600,400);
        loginViewStage.setResizable(false);
        loginViewStage.show();
        getStage(friendsTable).close();
    }
}
