def call(Map config) {

  def component = config.get("component", "");
  def distribution = config.get("distribution", "");
  def debFile = config.get("debFile");
  def repoName = config.get("repoName");

  sh """
    EXISTS=\$(aptly repo list --raw | grep "${repoName}")
    if [ -z "\$EXISTS" ]
    then
        aptly repo create -component="${component}" "${repoName}"
        aptly publish repo -distribution="${distribution}" -component="${component}" "${repoName}"
    fi

    aptly repo add "${repoName}" "${debFile}"

    aptly publish update "${distribution}"
  """
}