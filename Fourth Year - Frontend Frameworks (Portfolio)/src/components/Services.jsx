import React from 'react';
import PropTypes from 'prop-types';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import ScrollableAnchor from 'react-scrollable-anchor';
import { withStyles } from '@material-ui/core/styles';

const styles = theme => ({
    '@global': {
        body: {
            backgroundColor: theme.palette.common.white,
        },
    },
    appBar: {
        position: 'relative',
    },
    toolbarTitle: {
        flex: 1,
    },
    layout: {
        width: 'auto',
        marginLeft: theme.spacing.unit * 3,
        marginRight: theme.spacing.unit * 3,
        [theme.breakpoints.up(900 + theme.spacing.unit * 3 * 2)]: {
            width: 900,
            marginLeft: 'auto',
            marginRight: 'auto',
        },
    },
    heroContent: {
        margin: '0 auto',
        padding: `${theme.spacing.unit * 8}px 0 ${theme.spacing.unit * 6}px`,
    },
    cardHeader: {
        backgroundColor: theme.palette.grey[200],
    },
    cardPricing: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'baseline',
        marginBottom: theme.spacing.unit * 2,
    },
    cardActions: {
        [theme.breakpoints.up('sm')]: {
            paddingBottom: theme.spacing.unit * 2,
        },
    },
    footer: {
        marginTop: theme.spacing.unit * 8,
        borderTop: `1px solid ${theme.palette.divider}`,
        padding: `${theme.spacing.unit * 6}px 0`,
    },
});

const tiers = [
    {
        title: 'Java Development',
        price: '17.50',
        description: [
            'Perfect for Applications',
            'Free Consultation',
            'Infrastructure Available',
            'Email and Phone Support'
        ],
        buttonText: 'Start with Java',
        buttonVariant: 'contained',
    },
    {
        title: 'Node Development',
        price: '35.00',
        description: [
            'Perfect for Backend',
            'Free Consultation',
            'Infrastructure Available',
            'Email and Phone Support',
        ],
        buttonText: 'Start with Node',
        buttonVariant: 'contained',
    },
    {
        title: 'React Development',
        price: '52.50',
        description: [
            'Perfect for Frontend',
            'Free Consultation',
            'Infrastructure Available',
            'Email and Phone Support',
        ],
        buttonText: 'Start with React',
        buttonVariant: 'contained',
    },
];

function Pricing(props) {
    const { classes } = props;

    return (
        <React.Fragment>
            <CssBaseline />
            <main className={classes.layout}>
                <div className={classes.heroContent}>
                    <ScrollableAnchor id={'services'}>
                        <Typography component="h1" variant="h2" align="center" color="textPrimary" gutterBottom>
                            Services and Pricing
                        </Typography>
                    </ScrollableAnchor>
                    <Typography variant="h6" align="center" color="textSecondary" component="p">
                        Take a look at what I offer. Feel free to get in touch so I can see what is right for you!
                    </Typography>
                </div>
                <Grid container spacing={40} alignItems="flex-end">
                    {tiers.map(tier => (
                        <Grid item key={tier.title} xs={12} sm={tier.title === 'Enterprise' ? 12 : 6} md={4}>
                            <Card>
                                <CardHeader
                                    title={tier.title}
                                    subheader={tier.subheader}
                                    titleTypographyProps={{ align: 'center' }}
                                    subheaderTypographyProps={{ align: 'center' }}
                                    className={classes.cardHeader}
                                />
                                <CardContent>
                                    <div className={classes.cardPricing}>
                                        <Typography component="h2" variant="h3" color="textPrimary">
                                            €{tier.price}
                                        </Typography>
                                        <Typography variant="h6" color="textSecondary">
                                            /per hour
                                        </Typography>
                                    </div>
                                    {tier.description.map(line => (
                                        <Typography variant="subtitle1" align="center" key={line}>
                                            {line}
                                        </Typography>
                                    ))}
                                </CardContent>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            </main>
        </React.Fragment>
    );
}

Pricing.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Pricing);
