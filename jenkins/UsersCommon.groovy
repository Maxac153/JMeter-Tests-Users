class UsersCommon {
    // Создание map с параметрами для запуска
    static def create_map_param(multiLineString) {
        def variblesMap = multiLineString.split('\n').collectEntries {
            def (key, value) = it.split('=')
            [(key): value]
        }
        return variblesMap
    }

    // Создаём строку для передачи параметров в тест
    static def create_string_param(paramMap) {
        def variablesString = ''
        paramMap.each { key, value ->
            variablesString += "-J ${key}=${value} "
        }
        return variablesString
    }

    // PS тесты надо запускать на удалённой машине, а не там где установлен Jenkins (пока удалённой машины нет)
    // Запускать через ssh, а собирать логи через scp
    def start_jmeter_test(testName, testPath, logPath, commonTestParam, testParam) {
        // Общие параметры
        def commonVariablesMap = create_map_param(commonTestParam)
        def commonVariablesString = create_string_param(commonVariablesMap)

        // Параметры конкретного теста
        def variablesMap = create_map_param(testParam)
        def variablesString = create_string_param(variablesMap)

        sh "./apache-jmeter/bin/jmeter.sh -n -t ${testPath}/${testName}.jmx ${commonVariablesString} ${variablesString} -l ${logPath}/test_results_${testName}.jtl"
    }
}

Object getProperty(String name) {
    return this.getClass().getClassLoader().loadClass(name).newInstance()
}

return this