package org.devops

def publicarImage(projectGitName) {
    withCredentials([usernamePassword(credentialsId: "${env.TOKEN_ID}", passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
        sh "docker login -u ${env.DOCKERHUB_USERNAME} -p ${env.DOCKERHUB_PASSWORD}"
        sh "docker tag juancita/${projectGitName} ${env.DOCKERHUB_USERNAME}/${projectGitName}"
        sh "docker push ${env.DOCKERHUB_USERNAME}/${projectGitName}"
    }
}
