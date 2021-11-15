let csrf_name = $("meta[name='_csrf_header']").attr("content"),
    csrf_value = $("meta[name='_csrf']").attr("content");

$(document).ajaxSend(function (e, xhr) {
    xhr.setRequestHeader(csrf_name, csrf_value);
});

function fetcher(url, options) {
    const update = {...options};
    update.headers = {
        ...update.headers
    };
    update.headers[csrf_name] = csrf_value;
    return fetch(url, update);
}
