function getList() {
    $.ajax({
        url: `/board`,
        dataType: `json`,
        method: `get`,
        success: function(response) {
            console.log(response);
        },
        error: function(error) {
            console.log(error)
        }
    })
}

$().ready(function () {
   getList();
});