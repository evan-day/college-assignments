import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import Card from "@material-ui/core/Card/Card";
import CardActionArea from "@material-ui/core/CardActionArea/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia/CardMedia";
import CardContent from "@material-ui/core/CardContent/CardContent";
import CardActions from "@material-ui/core/CardActions/CardActions";
import ScrollableAnchor from 'react-scrollable-anchor'

import learnMoreAboutMe from '../images/learnmoreaboutme.jpg';
import viewMyPricing from '../images/view-my-pricing.jpg';
import getInTouch from '../images/get-in-touch.jpg';

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
        objectFit: 'cover',
    },
});

function CenteredGrid(props) {
    const { classes } = props;

    return (
        <div className={classes.root}>
            <div className={classes.heroContent}>
                <ScrollableAnchor id={'home'}>
                    <Typography component="h1" variant="h2" align="center" color="textSecondary" gutterBottom>
                        Evan Day | 22 | Software Engineer
                    </Typography>
                </ScrollableAnchor>
            </div>
            <Grid container spacing={24}>
                <Grid container spacing={24} align="center">
                    <Grid item xs>
                        <Card className={classes.card}>
                            <CardActionArea>
                                <CardMedia
                                    component="img"
                                    height="140"
                                    className={classes.media}
                                    image={learnMoreAboutMe}
                                />
                                <CardContent>
                                    <Typography gutterBottom variant="h5" component="h2">
                                        Learn More About Me
                                    </Typography>
                                    <Typography component="p">
                                        Learn more about who I am, what I do for a living and how I can help you and your business.
                                    </Typography>
                                </CardContent>
                            </CardActionArea>
                            <CardActions>
                                <Button size="small" color="primary" href="#about-me">
                                    Learn More
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                    <Grid item xs>
                        <Card className={classes.card}>
                            <CardActionArea>
                                <CardMedia
                                    component="img"
                                    height="140"
                                    className={classes.media}
                                    image={viewMyPricing}
                                />
                                <CardContent>
                                    <Typography gutterBottom variant="h5" component="h2">
                                        View My Pricing
                                    </Typography>
                                    <Typography component="p">
                                        Interested in working together? Take a quick look at my pricing and get what works best for you.
                                    </Typography>
                                </CardContent>
                            </CardActionArea>
                            <CardActions>
                                <Button size="small" color="primary" href="#services">
                                    View Pricing
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                    <Grid item xs>
                        <Card className={classes.card}>
                            <CardActionArea>
                                <CardMedia
                                    component="img"
                                    height="140"
                                    className={classes.media}
                                    image={getInTouch}
                                />
                                <CardContent>
                                    <Typography gutterBottom variant="h5" component="h2">
                                        Get In Touch
                                    </Typography>
                                    <Typography component="p">
                                        Feel like saying hello? Then just use whatever method you like best from the options that I support.
                                    </Typography>
                                </CardContent>
                            </CardActionArea>
                            <CardActions>
                                <Button size="small" color="primary" href="#contact">
                                    Get In Touch
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                </Grid>
            </Grid>
        </div>
    );
}

CenteredGrid.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(CenteredGrid);