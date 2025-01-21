package org.devops

def despliegueContenedor(projectGitName) {
    sh "docker pull juancita/${projectGitName}"
    sh """docker run -d --name ${projectGitName} \
    --network=${env.NameNetwork} -p 5174:5174 \
    --user root juancita/${projectGitName}
    """
}
