import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import ScrollableAnchor from 'react-scrollable-anchor';

import contactEmail from '../images/contact-email.jpg';
import contactPhone from '../images/contact-phone.jpg';

const styles = theme => ({
    root: {
        flexGrow: 1,
    },
    card: {
        maxWidth: 345,
    },
    media: {
        height: 140,
    },
    paper: {
        padding: theme.spacing.unit * 2,
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
});

function MediaCard(props) {
    const { classes } = props;
    return (
        <div className={classes.root}>
            <div className={classes.heroContent}>
                <ScrollableAnchor id='contact'>
                    <Typography component="h1" variant="h2" align="center" color="textPrimary" gutterBottom>
                        Contact
                    </Typography>
                </ScrollableAnchor>
                <Typography variant="h6" align="center" color="textSecondary" component="p">
                    Want to reach out? Get in touch easily and quickly with one of the methods below.
                </Typography>
            </div>
            <Grid container spacing={12} align="center">
                <Grid item xs>
                    <Card className={classes.card}>
                        <CardActionArea>
                            <CardMedia
                                className={classes.media}
                                image={contactEmail}
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="h2">
                                    Email
                                </Typography>
                                <Typography component="p">
                                    The easiest and most effective way of reaching out. Send an email to evan.day@mycit.ie to get in touch and I will get back to you.
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item xs>
                    <Card className={classes.card}>
                        <CardActionArea>
                            <CardMedia
                                className={classes.media}
                                image={contactPhone}
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="h2">
                                    Phone
                                </Typography>
                                <Typography component="p">
                                    If you are looking to have a good old fashioned conversation, then you can give me call at 0852716501.
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Grid>
            </Grid>
        </div>
    );
}

MediaCard.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(MediaCard);