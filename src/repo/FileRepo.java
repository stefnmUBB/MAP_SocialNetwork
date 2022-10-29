package repo;

import domain.Entity;
import domain.validators.IValidator;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class FileRepo<ID, E extends Entity<ID>> extends InMemoryRepo<ID,E> {
    String fileName;

    FileRepo(String fileName, IValidator<E> validator) {
        super(validator);
        this.fileName=fileName;
        load();
    }

    public E add(E entity) throws EntityAlreadyExistsException {
        entity = super.add(entity);
        if(entity!=null) {
            saveAppend(entity);
        }
        return entity;
    }

    public E update(E entity){
        entity = super.update(entity);

        saveAll();

        return entity;
    }

    public E remove(ID id){
        E entity = super.remove(id);
        if(entity!=null) {
            saveAll();
        }
        return entity;
    }

    public abstract E extractEntity(List<String> attributes);
    public abstract String entityAsString(E entity);

    private void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine())!=null) {
                if(Objects.equals(linie,"")) {
                    System.out.println("[DBG] Empty line");
                }
                List<String> attr= Arrays.asList(linie.split(";"));
                E e=extractEntity(attr);
                super.add(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EntityAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveAppend(E entity) {
        try {
            File file = new File(fileName);
            BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true));
            if(!newLineExists(file)) {
                bW.newLine();
            }
            bW.write(entityAsString(entity));
            bW.newLine();
            bW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAll() {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,false))) {
            for(var entity : getAll()) {
                bW.write(entityAsString(entity));
                bW.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean newLineExists(File file) throws IOException {
        // https://stackoverflow.com/questions/28795440/check-if-a-new-line-exists-at-end-of-file
        RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
        long fileLength = fileHandler.length() - 1;
        if (fileLength < 0) {
            fileHandler.close();
            return true;
        }
        fileHandler.seek(fileLength);
        byte readByte = fileHandler.readByte();
        fileHandler.close();

        if (readByte == 0xA || readByte == 0xD) {
            return true;
        }
        return false;
    }
}
