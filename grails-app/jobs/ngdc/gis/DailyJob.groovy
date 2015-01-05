package ngdc.gis


class DailyJob {
    static triggers = {
        cron name: 'dailyTrigger', cronExpression: "0 0 * * * ?"
    }

    def execute() {
        println "executing daily job..."
    }
}
