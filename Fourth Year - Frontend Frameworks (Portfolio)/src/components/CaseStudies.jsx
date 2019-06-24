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
                <ScrollableAnchor id='case-studies'>
                    <Typography component="h1" variant="h2" align="center" color="textPrimary" gutterBottom>
                        Case Studies
                    </Typography>
                </ScrollableAnchor>
                <Typography variant="h6" align="center" color="textSecondary" component="p">
                    Hear from some of my previous clients on the work I have done with them.
                </Typography>
            </div>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>BiaMaith</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        "BiaMaith started with a small site that was only suitable for recipes. At the time I didn't think we needed much more. How wrong I was. BiaMaith.ie soon became one of Ireland's busiest food websites. This was a nightmare as our hosting package couldn't handle the massive increase in hits that came from TV appearances and being featured in many different national and international media outlets. It was a constant juggling act, the more hits we got the bigger package we needed. In the end it cost us a fortune for hosting not to mention the time spent dealing with the hosting company etc.
                        <br/><br/>
                        <Typography>Then Evan Day came into our lives and everything changed very quickly. His knowledge and expertise have been invaluable. He's not only redesigned our site and met the brief perfectly, he also explained each step in a way we could understand. A few short months later, thanks to Evan we now have a brand new site that works perfectly and meets our needs in ways we didn't realise was possible. The front end looks fantastic and is very responsive while the back end runs smoothly. Thanks to Evans work our site hits doubled in the first month and our costs halved. I can't recommend Evan enough to any business looking to start or increase their Web presence without the hassles and big expenses normally involved. I'm excited about working with Evan going forward as he really opened our eyes to what's possible."</Typography>
                        <Typography variant="h6"color="textSecondary" component="p">
                            Liam Boland - Head Chef at BiaMaith
                        </Typography>
                    </Typography>
                </ExpansionPanelDetails>
            </ExpansionPanel>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>Venice Art Guide</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        "Evan responded to my query so quickly I couldn't believe that he had understood my requirements, but he had! I've been delighted with his work, particularly his responsiveness and ability to recommend creative solutions to problems that have been difficult for me to explain. Evan's fees are very reasonable and quite honestly he has saved me money over what I could have done myself, as hosting charges have dropped massively since moving to an AWS hosted site. Check out my site - I'm delighted with it and it's the best recommendation I can think of - it was delivered way ahead of the schedule I had in mind, and it's proven a useful tool in driving engagements with my app and marketing."
                        <Typography variant="h6" color="textSecondary" component="p">
                            Stephen Brophy - Venice Art Guide
                        </Typography>
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

