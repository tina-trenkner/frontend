define([
    'react',
    'lodash/objects/assign'
], function (
    React,
    assign
) {
    var ConfirmButton = React.createClass({

        getInitialState: function () {
            this.timeout = this.props.timeout || 2000;
            return {
                confirming: false
            };
        },

        classNames : function(props) {
            var out = [];
            for(var f in props){
                if(props[f] === true) { out.push(f); }
            }
            return out.join(" ");
        },

        confirm: function () {
            if (this.state.confirming) {
                this.setState({
                    confirming: false
                });
                this.props.onClick();
            } else {
                this.setState({
                    confirming: true
                });
                setTimeout(function () {
                    this.setState({
                        confirming: false
                    });
                }.bind(this), this.timeout);
            }
        },

        render: function () {
            var inner = this.state.confirming ?
                'Confirm ' + this.props.text.toLowerCase() : this.props.text;

            return React.createElement(
                'button',
                assign({}, this.props, {
                    onClick: this.confirm,
                    className: 'crossword__controls__button--confirm'
                }, this),
                inner
            );
        }
    });

    return ConfirmButton;
});
