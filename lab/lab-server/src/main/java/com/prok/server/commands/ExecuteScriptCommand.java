package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class ExecuteScriptCommand implements Command {
    private final Collection collection;
    public ExecuteScriptCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        return null;
//        if (arg == null || "".equals(arg)) {
//            throw new IllegalArgumentException("Не передан адрес файла-скрипта.");
//        }
//        Scanner old_in = collection.getIn();
//
//        File file = new File(arg);
//        if (!file.exists() || file.isDirectory()) {
//            throw new IllegalArgumentException("Путь к файлу-скрипту указан неверно.");
//        } else if (ExecuteScriptPath.isInList(arg)) {
//            throw new IllegalArgumentException("Файл вызывает сам себя.");
//        } else {
//            ExecuteScriptPath.addPath(arg);
//
//            try {
//                Scanner new_in = new Scanner(file);
//                collection.setIn(new_in);
//
//                ListenProcess fileClient = new ListenProcess(collection);
//                fileClient.startProcess();
//            } catch (NoSuchElementException e) {
//                System.out.println("Выполнение скрипта закончилось.");
//            } catch (FileNotFoundException e) {
//                System.out.println("Файл скрипта не найден.");
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//
//            collection.setIn(old_in);
//            ExecuteScriptPath.removePath(arg);
//        }
//

    }
}
