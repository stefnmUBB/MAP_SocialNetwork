package service;

import domain.Friendship;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class Community {
    private final Network network;
    Map<Long, List<Long>> adj = new HashMap<>();

    public Community(Network network, Long iduser) {
        this.network = network;
        adj.put(iduser, new ArrayList<>());
        discover(iduser);
    }

    public boolean contains(Long uid) {
        return adj.containsKey(uid);
    }
    private void discover(Long uid) {
        Stack<Long> st = new Stack<>();
        st.push(uid);

        while(!st.empty()) {
            Long id = st.pop();
            for(var f:network.getAllFriendships()) {
                if(f.containsUser(id)) {
                    Long otherId = f.getTheOtherOne(id);
                    //System.out.println(id+" "+otherId);

                    if(!adj.containsKey(otherId)) {
                        st.push(otherId);
                    }

                    addFriends(id, otherId);
                }
            }
        }
    }

    public int getLongestPathLength() {
        if(size()<=1) return 0;
        if(size()==2) return 1;
        List<Path> paths = new ArrayList<>();
        List<Path> store = new ArrayList<>();
        List<Path> dumper = new ArrayList<>();

        List<Path> edges = new ArrayList<>();
        List<Path> edump = new ArrayList<>();

        for(var u : adj.keySet()) {
            for(var v : adj.get(u)) {
                if(u<v) {
                    paths.add(new Path(u,v));
                    edges.add(new Path(u,v));
                }
            }
        }
        System.out.println("Edges count = "+paths.size());
        int globalmax=0;
        for(int k=0;k<size();k++) {
            int lenmax=globalmax;
            for (Path path : paths) {
                int cnt = 0;
                for (var e : edges) {
                    if (path.canJoin(e)) {
                        var p = path.join(e);
                        store.add(p);
                        lenmax = max(lenmax, p.length());
                        cnt++;
                    }
                }
                if (cnt == 0) {
                    dumper.add(path);
                }
            }

            for(var e:edges) {
                int cnt=0;
                for(var p:paths) {
                    if(p.canJoin(e))
                        cnt++;
                }
                if(cnt==0)
                {
                    edump.add(e);
                }
            }

            paths.clear();
            paths.addAll(store);
            store.clear();
            paths.removeAll(dumper);
            System.out.println("[DBG] Dump len = "+dumper.size());
            dumper.clear();

            edges.removeAll(edump);
            System.out.println("[DBG] Edges dump len = "+edump.size());
            edump.clear();
            if(globalmax==lenmax)
                break;
            globalmax=lenmax;

            System.out.println("[DBG] Partial max path len = "+globalmax);
            System.out.println("[DBG] New cache size = " + paths.size());
        }
        return globalmax;

    }

    private void addItem(Long k, Long i) {
        if(!adj.containsKey(k)) {
            adj.put(k, new ArrayList<>());
        }
        if(!adj.get(k).contains(i))
            adj.get(k).add(i);
    }

    private void addFriends(Long id1, Long id2) {
        addItem(id1, id2);
        addItem(id2, id1);
    }

    public int size() {return adj.keySet().size();}

    public String toString() {
        return adj.keySet().stream().map(Object::toString).collect(Collectors.joining(", "));
    }

}
