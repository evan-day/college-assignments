import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Button from '@material-ui/core/Button';
import ScrollableAnchor from 'react-scrollable-anchor'

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
                <ScrollableAnchor id='portfolio'>
                    <Typography component="h1" variant="h2" align="center" color="textPrimary" gutterBottom>
                        Portfolio
                    </Typography>
                </ScrollableAnchor>
                <Typography variant="h6" align="center" color="textSecondary" component="p">
                    Here is a small sampling of some of my most successful clients. I’ve worked with each one directly to ensure their website is exactly as they want it. You can visit their websites by clicking the button in their individual sections. I have also included some information surrounding my research work in college.
                </Typography>
            </div>
            <ExpansionPanel align="center">
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>BiaMaith</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        My work with BiaMaith lead to the relaunch of their site, generating four times more traffic in the previous week with additional capacity available on demand to them. The net capacity gained, has ensured massive cost reductions for BiaMaith in comparison with the same amount of capacity with other providers. <br/>
                        <Button variant="contained" className={classes.button} fullWidth="true" color="primary" href="https://biamaith.ie">
                            Visit Their Website Now
                        </Button>
                    </Typography>
                </ExpansionPanelDetails>
            </ExpansionPanel>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>Venice Art Guide</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        Venice Art Guide have an intuitive app for learning all about the amazing art work around Venice. With Google Maps integration, users of the app can find the quickest path to the work they want to see. The site was built by hand over a period of a month on their own AWS account. Eventually, they migrated to our account as part of our moving clients into our new support program. We had also just released new security solutions for DDOS mitigation that they avail of for free as part of their annual hosting.<br/>
                        <Button variant="contained" className={classes.button} fullWidth="true" color="primary" href="https://veniceartguide.com">
                            Visit Their Website Now
                        </Button>
                    </Typography>
                </ExpansionPanelDetails>
            </ExpansionPanel>
            <ExpansionPanel>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                    <Typography className={classes.heading}>Final Year Research Project</Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <Typography>
                        As part of my degree, I am undertaking a research project that contributes 25% to my overall final grade. In this project, I will be answering the following question. Can I build an automation platform for the creation of Continuous Integration and Continuous Delivery pipelines? I have made great progress on answering this question and I am looking forward to delivering the final project in the middle of 2019.
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

