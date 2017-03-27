import requests
import json

def get_assignment(application, experiment, user):
    """
    Get's an assignment for the given user and experiment. If no
    assignment exists one will be generated.

    Args:
        application: the application the experiment runs in
        experiment:  the running experiment for which the assignment should be retrieved
        user:        the user who should be assigned
    """
    urlAssignment = "https://abtesting.intuit.com/api/v1/assignments/applications/%s/experiments/%s/users/%s" %(application, experiment, user);
    r = requests.get(urlAssignment)
    try:
        assignment = json.loads(r.text)['assignment']
    except KeyError as e:
        if "EXPERIMENT_NOT_FOUND" in r.text:
            print('The given Experiment is not found')
        else:
            # further exception handling should happen here
            raise

if __name__ == "__main__":
    application = 'ApplicationName'
    experiment = 'ExperimentName'
    user = 'UserName'

    print(get_assignment(application, experiment, user))
