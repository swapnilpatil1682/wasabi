import requests
import json

def post_action(application, experiment, user):
    """
    Records an action for the given user and experiment.

    Args:
        application: the application the experiment runs in
        experiment:  the running experiment for which the event should be recorded
        user:        the user who should be assigned
    """

    urlAssignment = "http://abtesting.intuit.com/api/v1/events/applications/%s/experiments/%s/users/%s" %(application, experiment, user);

    headers = {'content-type': 'application/json'}
    # The actoin name can be freely assigned and an additional payload is
    # used for segmentation rules or available for later analysis
    events = {'events':[{'name':'ButtonClicked', 'payload':'{\'state\':\'CA\'}'}]}

    r = requests.post(urlAssignment, data = json.dumps(events), headers=headers)

    if r.status_code == 201: # when the request returns 201 the action was recorded correctly
        return True
    return False

if __name__ == "__main__":
    application = 'ApplicationName'
    experiment = 'ExperimentName'
    user = 'UserName'

    print('Action recorded' if post_action(application, experiment, user) else 'Action not recorded')
