def call(final pipelineContext, final stageConfig) {
    MOJO_DAI_VERSION = '1.4.0'
    withCredentials([string(credentialsId: "DRIVERLESS_AI_LICENSE_KEY", variable: "DRIVERLESS_AI_LICENSE_KEY"]) {
        sh "echo ${DRIVERLESS_AI_LICENSE_KEY} > ${WORKSPACE}/${stageConfig.stageDir}/license.sig"
        try {
            withEnv(["DRIVERLESS_AI_LICENSE_FILE=${WORKSPACE}/${stageConfig.stageDir}/license.sig", "MOJO_PIPELINE_TEST_DIR=/home/0xdiag/migration_mojo/${MOJO_DAI_VERSION}/test_mojo_package"]) {
                def defaultStage = load('h2o-3/scripts/jenkins/groovy/defaultStage.groovy')
                defaultStage(pipelineContext, stageConfig)
            }
        } finally {
            sh "rm -f ${WORKSPACE}/${stageConfig.stageDir}/license.sig"
        }
    }
}

return this
