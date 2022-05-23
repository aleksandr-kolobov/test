$(function(){

    const appendTarget = function(data){
        var targetCode = '<a href="#" class="target-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#target-list')
            .append('<div>' + targetCode + '</div>');
    };

    /*Loading targets on load page
    $.get('/targets/', function(response){
        for(i in response) {
            appendTarget(response[i]);
        }
    });*/

    //Show adding target form
    $('#show-add-target-form').click(function(){
        $('#target-form').css('display', 'flex');
    });

    //Closing adding target form
    $('#target-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting target
    $(document).on('click', '.target-link', function(){
        var link = $(this);
        var targetId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/targets/' + targetId,
            success: function(response){
                var code = '<span>Время для выполнения:' + response.time + '</span>';
                link.parent().append(code);
            },
            error: function(response){
                if(response.status == 404) {
                    alert('Дело под таким номером не найдено!');
                }
            }
        });
        return false;
    });

    //Adding target
    $('#save-target').click(function(){
        var data = $('#target-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/targets/',
            data: data,
            success: function(response){
                $('#target-form').css('display', 'none');
                var target = {};
                target.id = response;
                var dataArray = $('#target-form form').serializeArray();
                for(i in dataArray) {
                    target[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTarget(target);
            }
        });
        return false;
    });

    //Delete targets
    $('#delete-all-targets').click(function(){
        $.ajax({
            method: "DELETE",
            url: '/targets/',
            success: function(response){
                alert('Все удалено! Перезагрузите страницу!');
            }
        });
        return false;
    });
});