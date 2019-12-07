let COLDIGO = new Object

$(document).ready(() => {
    $("header").load("/coldigoGeladeiras/pages/admin/general/header.html")
    $("footer").load("/coldigoGeladeiras/pages/admin/general/footer.html")

    COLDIGO.empty = (value) => {
        if (value == 'undefined' || value == undefined || typeof value == 'undefined') {
            return true
        }
        
        if (value == "") {
            return true
        }    

        if (value.length < 1) {
            return true
        }

        if (!isNaN(value)) {
            if (value == 0 || value == "0" || value == 0.0 || value == "0.0" || value == 0.00 || value == "0.00") {
                return true
            }
        }

        return false
    }

    COLDIGO.carregaPagina = (pagename) => {
        $("section").empty()
        $("section").load(pagename + "/", (response, status, info) => {
            if (status == "error") {
                var msg = "Houve um erro ao encontrar a página: " + info.status + " - " + info.statusText
                $("section").html(msg)
            }
        })
    }

    COLDIGO.exibirAviso = (aviso) => {
        let modal = {
            title: "Mensagem",
            height: 250,
            width: 400,
            modal: true,
            buttons: {
                "OK": function() {
                    $(this).dialog("close")
                }
            }
        }

        $("#modalAviso").html(aviso)
        $("#modalAviso").dialog(modal)
    }
})