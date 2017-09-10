'use strict';

const React = require('react');

import Navbar from './components/Navbar'

export default class HabitPage extends React.Component { 
    render () {
        return (
            <div>
                <Navbar/>
                <div className="container">
                    <div className="row">
                        <div className="col-sm-3 col-md-2 sidebar">
                            <ul className="nav nav-sidebar">
                                <li><a href="#">Summary (Not implemented) </a></li>
                                <li><a href="/habit">All Habits</a></li>
                                <li><a href="/user-habit">Habits</a></li>
                                <li><a href="#">Tasks (Not implemented)</a></li>
                                <li><a href="#">Challenges (Not implemented)</a></li>
                                <li><a href="#">Competition (Not implemented)</a></li>
                            </ul>
                        </div>
                        <div className="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">


                        </div>
                    </div>
                </div>
            </div>
        );
    }
}