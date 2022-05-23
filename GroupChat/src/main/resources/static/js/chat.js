$(function(){

    let getMessageElement = function(message){
        let item = $('<div class="message-item"></div>');
        let header = $('<div class="message-header"></div>');
        header.append($('<div class="date-time">' + message.datetime + '</div>'));
        header.append($('<div class="user-name">' + message.username + '</div>'));
        let textEl = $('<div class="message-text"></div>');
        textEl.text(message.text);
        item.append(header, textEl);
        return item;
    }

    let updateMessages = function(){
        $.get('/messages', {}, function(response){
            if(response.length == 0){
                $('.messages-list').html('<i>сообщений нет</i>');
                return;
            }
            $('.messages-list').html('');
            for(i in response){
                let element = getMessageElement(response[i])
                $('.messages-list').append(element);
            }
        })
    }

    let updateUsers = function(){
        $.get('/users', {}, function(response){
            if(response.length == 0){
                $('.users-list').html('<i>пользователей нет</i>');
                return;
            }
            $('.users-list').html('');
            for(i in response){
                let element = $('<div>' + response[i] + '</div>');
                $('.users-list').append(element);
            }
        })
    }

    let initApplication = function(){
        $('.message-and-users').css({display: 'flex'});
        $('.controls').css({display: 'flex'});

        setInterval(updateMessages, 2000);
        setInterval(updateUsers, 5000);
    }

    let registerUser = function(name){
        $.post('/auth', {name: name}, function(response){
            if(response.result){
                initApplication();
            }
        })
    }

    $.get('/init', {}, function(response){
        if(response.result){
            initApplication();
        } else{
            let name = prompt('Введите Ваше имя:');
            registerUser(name);
        }
    })

    $('.send-message').on('click', function(){
        let str = $('.new-message').val();
        $.post('/message', {str: str}, function(response){
            if(response.result){
                $('.new-message').val('');
            } else{
                alert('Не отправляйте пустые! Ошибка!');
            }
        })
    })


})