import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Typography from "@material-ui/core/Typography/Typography";
import Card from "@material-ui/core/Card/Card";
import CardActionArea from "@material-ui/core/CardActionArea/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia/CardMedia";
import ScrollableAnchor from 'react-scrollable-anchor'

import whatIDo from '../images/what-i-do.jpg';
import whatIOffer from '../images/what-i-offer.jpg';
import whoIAm from '../images/who-i-am.jpg';

const styles = theme => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing.unit * 2,
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
    heroContent: {
        margin: '0 auto',
        padding: `${theme.spacing.unit * 8}px 0 ${theme.spacing.unit * 6}px`,
    },
    card: {
        maxWidth: 345,
    },
    media: {
        height: 140,
    },
});

function AutoGrid(props) {
    const { classes } = props;

    return (
        <div className={classes.root}>
            <div className={classes.heroContent}>
                <ScrollableAnchor id={'about-me'}>
                    <Typography component="h1" variant="h2" align="center" color="textSecondary" gutterBottom>
                        About Me
                    </Typography>
                </ScrollableAnchor>
            </div>
            <Grid container spacing={24}>
                <Grid item xs={3}>
                    <Card className={classes.card}>
                        <CardActionArea>
                            <CardMedia
                                className={classes.media}
                                image={whoIAm}
                            />
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item xs={9}>
                    <Paper className={classes.root} elevation={1} align="center">
                        <Typography variant="h5" component="h3">
                            Who I Am
                        </Typography>
                        <Typography component="p">
                            I’m 22 years old and entering my final year of study in Software Development and Networking, in Cork Institute of Technology. I have recently completed a nine month placement with McKesson, a fortune six company, as a Software Engineer Intern with a primary focus in Developer Services. I continue to work with McKesson in my previous position on a part time basis. My experience is primarily in cloud, with varying levels of experience across Amazon Web Services, Microsoft Azure and Google Cloud Platform in terms of cloud providers. I also have experience in Docker, Kubernetes and CI/CD technologies.
                        </Typography>
                    </Paper>
                </Grid>
                <Grid item xs={9}>
                    <Paper className={classes.root} elevation={1} align="center">
                        <Typography variant="h5" component="h3">
                            What I Offer
                        </Typography>
                        <Typography component="p">
                            I offer to you a way to scale your business out. You do not need to learn how to pick the best hosting provider or how to run advertising on social media. You just need to pick the services that you need, from what I offer. Following this, we will work together to ensure the success of your business. It’s never been easier to get started online and with my services, you can be certain it will be a great start, middle and finish.
                        </Typography>
                    </Paper>
                </Grid>
                <Grid item xs={3}>
                    <Card className={classes.card}>
                        <CardActionArea>
                            <CardMedia
                                className={classes.media}
                                image={whatIOffer}
                            />
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item xs={3}>
                    <Card className={classes.card}>
                        <CardActionArea>
                            <CardMedia
                                className={classes.media}
                                image={whatIDo}
                            />
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item xs={9}>
                    <Paper className={classes.root} elevation={1} align="center">
                        <Typography variant="h5" component="h3">
                            What I Do
                        </Typography>
                        <Typography component="p">
                            I’m passionate about Kubernetes and function as a service technologies. I work for myself under the name Suvoken Cloud Solutions and this is something I love to work on. My business focuses on the managing of every possible internet service for a client. This can be as simple as running the web hosting for a client, to as complex as their entire business online. Clients can pay hourly for work or subscribe to my services for a vastly reduced rate.
                        </Typography>
                    </Paper>
                </Grid>
            </Grid>
        </div>
    );
}

AutoGrid.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(AutoGrid);
