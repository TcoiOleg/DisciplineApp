'use strict';

const React = require('react');
const client = require('./modules/client');

import Navbar from './components/Navbar'
import Habit from './components/Habit'
import HabitHistoryTable from './components/HabitHistoryTable'

export default class HabitPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {habit:{}, user:{}};
        this.showCompetition = this.showCompetition.bind(this);
        this.showChallenges = this.showChallenges.bind(this);
    }

    componentDidMount() {
        this.loadFromServer();
    }

    loadFromServer() {
        var href = window.location.href;
        var id = href.substr(href.lastIndexOf('\\'));
        client({
            method: 'GET',
            path: '/api/habits/' + id,
            headers: {
                'X-CSRF-TOKEN': $("meta[name='_csrf']").attr("content")
            }
        }).done(response => {
            this.setState({habit:response.entity});
            client({
                method: 'GET',
                path: response.entity._links.habitUser.href,
                headers: {
                    'X-CSRF-TOKEN': $("meta[name='_csrf']").attr("content")
                }
            }).done(response => this.setState({user:response.entity}));
        });
    }

    showCompetition() {
        if (this.state.user) {
            if (this.state.user.hidden != null && !this.state.user.hidden) {
                return (<li><a href="#">Competition (Not implemented)</a></li>);
            }
        }
    }

    showChallenges() {
        if (this.state.user) {
            if (this.state.user.hidden != null && !this.state.user.hidden) {
                return (<li><a href="/challenge">Challenges</a></li>);
            }
        }
    }

    render () {
        return (
            <div>
                <Navbar user={this.state.user} />
                <div className="container">
                    <div className="row">
                        <div className="col-sm-3 col-md-2 sidebar">
                            <ul className="nav nav-sidebar">
                                <li><a href="/summary">Summary</a></li>
                                <li><a href="/user-habit">Habits</a></li>
                                <li><a href="#">Tasks (Not implemented)</a></li>
                                {this.showChallenges()}
                                {this.showCompetition()}
                            </ul>
                        </div>
                        <div className="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                            <Habit habit={this.state.habit} />
                            <HabitHistoryTable habit={this.state.habit}/>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}