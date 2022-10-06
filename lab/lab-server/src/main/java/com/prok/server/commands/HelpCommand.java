package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class HelpCommand implements Command {
    private Collection collection;
    public HelpCommand(Collection collection) {
        this.collection = collection;
    }
    @Override
    public Response execute(Request request) {
        if (request.args != null) {
            return new Response(false, "Эта команда не поддерживает аргументы");
        }
        String out = "Список доступных команд:\n";
        out += "    help : вывести справку по доступным командам\n"
                + "    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n"
                + "    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"
                + "    add {element} : добавить новый элемент в коллекцию\n"
                + "    update id {element} : обновить значение элемента коллекции, id которого равен заданному\n"
                + "    remove_by_id id : удалить элемент из коллекции по его id\n"
                + "    clear : очистить коллекцию\n"
                + "    save : сохранить коллекцию в файл\n"
                + "    execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"
                + "    exit : завершить программу (без сохранения в файл)\n"
                + "    remove_last : удалить последний элемент из коллекции\n"
                + "    shuffle : перемешать элементы коллекции в случайном порядке\n"
                + "    remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n"
                + "    filter_less_than_distance distance : вывести элементы, значение поля distance которых меньше заданного\n"
                + "    filter_greater_than_distance distance : вывести элементы, значение поля distance которых больше заданного\n"
                + "    print_ascending : вывести элементы коллекции в порядке возрастания";

        return new Response(true, out);
    }
}
