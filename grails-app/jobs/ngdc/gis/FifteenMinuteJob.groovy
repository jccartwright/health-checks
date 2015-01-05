package ngdc.gis



class FifteenMinuteJob {
    static triggers = {
        cron name: '15minuteTrigger', cronExpression: "0 0/15 * * * ?"
    }

    def execute() {
        println "executing 15 minutes job..."
    }
}
