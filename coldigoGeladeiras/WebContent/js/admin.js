let COLDIGO = new Object

$(document).ready(() => {
    $("header").load("/coldigoGeladeiras/pages/admin/general/header.html")
    $("footer").load("/coldigoGeladeiras/pages/admin/general/footer.html")

    COLDIGO.carregaPagina = (pagename) => {
        $("section").empty()
        $("section").load(pagename + "/", (response, status, info) => {
            if (status == "error") {
                var msg = "Houve um erro ao encontrar a página: " + info.status + " - " + info.statusText
                $("section").html(msg)
            }
        })
    }
})