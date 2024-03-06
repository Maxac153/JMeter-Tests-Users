#!groovy
package jenkins

class StartTest {
    static def pr() {
        echo "Hello"
    }

    // Создание map с параметрами для запуска
    static def create_map_param(multiLineString) {
        def variblesMap = [:]
        multiLineString.split('/n').each { line ->
            def parts = line.split('=')
            variblesMap[parts[0]] = parts[1]
        }
        return variblesMap
    }

    // Создаём строку для передачи параметров в тест
    static def create_string_param(paramMap) {
        def variablesString = ''
        paramMap.each { key, value ->
            variablesString += "-D${key}=${value} "
        }
        return variablesString
    }

    // PS тесты надо запускать на удалённой машине, а не там где установлен Jenkins (пока удалённой машины нет)
    // Запускать через ssh, а собирать логи через scp
    static def start_jmeter_test(testName, testPath, logPath, commonTestParam, testParam) {
        echo "=========== ${testName} ==========="

        // Общие параметры
        def commonVariblesMap = create_map_param(commonTestParam)
        def commonVariablesString = create_string_param(commonVariblesMap)

        // Параметры конкретного теста
        def variblesMap = create_map_param(testParam)
        def variablesString = create_string_param(variblesMap)

        sh " JMETER_OPTS=\"-Duser.language=en -Duser.country=US -Duser.variant=UTF-8 ${commonVariablesString}${variablesString}\" jmeter.sh -n –t ${testPath}/${testName}.jmx -l ${logPath}/test_results_(${testName}).jtl"
    }
}
