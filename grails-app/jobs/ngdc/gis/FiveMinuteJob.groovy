package ngdc.gis


class FiveMinuteJob {
    def healthCheckService

    static triggers = {
        cron name: '5minuteTrigger', cronExpression: "0 0/5 * * * ?"
    }

    def execute() {
        println "running 5 minutes job"
        def checks = HealthCheck.findByCheckInterval('5minutes')
        checks.each { check ->
            healthCheckService.run(check)
        }
    }
}
