import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ScrollableAnchor from 'react-scrollable-anchor';

const styles = theme => ({
    root: {
        width: '100%',
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        fontWeight: theme.typography.fontWeightRegular,
    },
    heroContent: {
        margin: '0 auto',
        padding: `${theme.spacing.unit * 8}px 0 ${theme.spacing.unit * 6}px`,
    },
});

function SimpleExpansionPanel(props) {
    const { classes } = props;
    return (
        <div className={classes.root}>
            <div className={classes.heroContent}>
                <ScrollableAnchor id='process'>
                    <Typography component="h1" variant="h2" align="center" color="textPrimary" gutterBottom>
                        Process
                    </Typography>
                </ScrollableAnchor>
                <Typography variant="h6" align="center" color="textSecondary" component="p">
                    Learn more about the various stages of working with me on a project.
                </Typography>
            </div>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>One - Consultation</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        The first part of any project is the consultation. We will have a meeting where we get to know each other a bit more and talk about the project. Throughout the project, we will have more meetings where I keep you up to date on the project in the various stages of development. You will never be in the dark when it comes to your project.
                    </Typography>
                </ExpansionPanelDetails>
            </ExpansionPanel>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>Two - Mock Up</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        I will create a quick, visual mockup of your proposed application. This is one of the best times to be as vocal as possible during the life cycle of the project. Since no large amount of development work has taken place, it is easy to implement any suggestions that you may have at this stage. While you’re free to make changes at any stage during the project, it is obviously cheaper to make them earlier than later.
                    </Typography>
                </ExpansionPanelDetails>
            </ExpansionPanel>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>Three - Development</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        In this stage, I will be actively developing your project. I will keep you up to date on a weekly basis of how much progress is being made. You’ll be able to follow me on my development journey towards a working prototype.
                    </Typography>
                </ExpansionPanelDetails>
            </ExpansionPanel>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>Four - Prototype</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        When development has reached the stage of a working prototype, you will be shown the prototype in a meeting. In this meeting, you have the opportunity to deliver feedback and requests for items to be changed. These prototypes are always exceptionally close to the finished product, so if you approve of the prototype, final delivery can happen.
                    </Typography>
                </ExpansionPanelDetails>
            </ExpansionPanel>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>Five - Delivery</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        Finally, we have the delivery aspect of the project. All projects get infrastructure management, so I will deploy your application onto the correct infrastructure so you don’t have to. Once the application is up and running, the project is considered complete.
                    </Typography>
                </ExpansionPanelDetails>
            </ExpansionPanel>
        </div>
    );
}

SimpleExpansionPanel.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SimpleExpansionPanel);

