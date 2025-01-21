package org.devops

def despliegueContenedor(projectGitName){
    sh "docker stop ${projectGitName.toLowerCase()}"
    sh "docker rm ${projectGitName.toLowerCase()}"
    sh "docker pull lainaus/${projectGitName.toLowerCase()}"
    sh """docker run -d --name ${projectGitName.toLowerCase()} \
    --network=${env.NameNetwork} -p 5174:5174 \
    --user root lainaus/${projectGitName.toLowerCase()}
    """
}
