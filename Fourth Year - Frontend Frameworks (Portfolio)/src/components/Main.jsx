import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import { withStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import HomeIcon from '@material-ui/icons/Home';
import MailIcon from '@material-ui/icons/Mail';
import PortraitIcon from '@material-ui/icons/Portrait';
import WebIcon from '@material-ui/icons/Web';
import PersonIcon from '@material-ui/icons/Person';
import SyncIcon from '@material-ui/icons/Sync';
import BookIcon from '@material-ui/icons/Book';
import Services from './Services';
import Home from './Home';
import AboutMe from './AboutMe';
import Portfolio from './Portfolio';
import Contact from './Contact';
import CaseStudies from './CaseStudies';
import Process from './Process';

const drawerWidth = 240;

const styles = theme => ({
    root: {
        display: 'flex',
    },
    appBar: {
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
    },
    appBarShift: {
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: drawerWidth,
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    menuButton: {
        marginLeft: 12,
        marginRight: 20,
    },
    hide: {
        display: 'none',
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
    },
    drawerHeader: {
        display: 'flex',
        alignItems: 'center',
        padding: '0 8px',
        ...theme.mixins.toolbar,
        justifyContent: 'flex-end',
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        marginLeft: -drawerWidth,
    },
    contentShift: {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        marginLeft: 0,
    },
    paper: {
        padding: theme.spacing.unit * 2,
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
});

function ListItemLink(props) {
    return <ListItem button component="a" {...props} />;
}


class Main extends React.Component {
    state = {
        open: false,
    };

    handleDrawerOpen = () => {
        this.setState({ open: true });
    };

    handleDrawerClose = () => {
        this.setState({ open: false });
    };

    render() {
        const { classes, theme } = this.props;
        const { open } = this.state;

        return (
            <div className={classes.root}>
                <CssBaseline />
                <AppBar
                    position="fixed"
                    className={classNames(classes.appBar, {
                        [classes.appBarShift]: open,
                    })}
                >
                    <Toolbar disableGutters={!open}>
                        <IconButton
                            color="inherit"
                            aria-label="Open drawer"
                            onClick={this.handleDrawerOpen}
                            className={classNames(classes.menuButton, open && classes.hide)}
                        >
                            <MenuIcon />
                        </IconButton>
                    </Toolbar>
                </AppBar>
                <Drawer
                    className={classes.drawer}
                    variant="persistent"
                    anchor="left"
                    open={open}
                    classes={{
                        paper: classes.drawerPaper,
                    }}
                >
                    <div className={classes.drawerHeader}>
                        <IconButton onClick={this.handleDrawerClose}>
                            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                        </IconButton>
                    </div>
                    <Divider />
                    <List>
                        <ListItem button>
                            <ListItemIcon>
                                <HomeIcon />
                            </ListItemIcon>
                            <ListItemLink href="#home">
                                <ListItemText primary="Home" />
                            </ListItemLink>
                        </ListItem>
                        <ListItem button>
                            <ListItemIcon>
                                <PortraitIcon />
                            </ListItemIcon>
                            <ListItemLink href="#about-me">
                                <ListItemText primary="About Me" />
                            </ListItemLink>

                        </ListItem>
                        <ListItem button>
                            <ListItemIcon>
                                <WebIcon />
                            </ListItemIcon>
                            <ListItemLink href="#services">
                                <ListItemText primary="Services"/>
                            </ListItemLink>
                        </ListItem>
                        <ListItem button>
                            <ListItemIcon>
                                <PersonIcon />
                            </ListItemIcon>
                            <ListItemLink href="#portfolio">
                                <ListItemText primary="Portfolio" />
                            </ListItemLink>
                        </ListItem>
                        <ListItem button>
                            <ListItemIcon>
                                <MailIcon />
                            </ListItemIcon>
                            <ListItemLink href="#contact">
                                <ListItemText primary="Contact" />
                            </ListItemLink>
                        </ListItem>
                    </List>
                    <Divider />
                    <List>
                        <ListItem button>
                            <ListItemIcon>
                                <SyncIcon />
                            </ListItemIcon>
                            <ListItemLink href="#process">
                                <ListItemText primary="Process" />
                            </ListItemLink>
                        </ListItem>
                        <ListItem button>
                            <ListItemIcon>
                                <BookIcon />
                            </ListItemIcon>
                            <ListItemLink href="#case-studies">
                                <ListItemText primary="Case Studies" />
                            </ListItemLink>
                        </ListItem>
                    </List>
                </Drawer>
                <main
                    className={classNames(classes.content, {
                        [classes.contentShift]: open,
                    })}
                >
                    <div className={classes.drawerHeader} />
                    <Home/>
                    <AboutMe/>
                    <Services/>
                    <Portfolio/>
                    <Contact/>
                    <Process/>
                    <CaseStudies/>
                </main>
            </div>

        );
    }
}

Main.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(Main);