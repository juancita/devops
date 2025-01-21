package org.devops

def AnalisisOwasp(projectGitName) {
    sh """docker run --rm -v ProjectOwasp:/zap/wrk/:rw \
            --user root --network=${env.NameNetwork} \
            owasp/zap2docker-stable zap-full-scan.py \
            -t ${env.dominio} \
            -r ProjectOwasp.html -I
            """
}
