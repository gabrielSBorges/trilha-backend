COLDIGO.produto = new Object()

$(document).ready(function() {
    COLDIGO.produto.carregarMarcas = function() {
        $.ajax({
            type: "GET",
            url: "/coldigoGeladeiras/rest/marca/buscar",
            success: function(marcas) {
                if (!COLDIGO.empty(marcas)) {
                    $("#selMarca").html("")

                    let option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Escolha")
                    $("#selMarca").append(option)

                    for (const i in marcas) {
                        let option = document.createElement("option")
                        option.setAttribute("value", marcas[i].id)
                        option.innerHTML = (marcas[i].nome)
                        $("#selMarca").append(option)
                    }
                } else {
                    $("#selMarca").html("")

                    let option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Cadastre uma marca primeiro!")
                    $("#selMarca").append(option)
                    $("#selMarca").addClass("aviso")
                }
            },
            error: function(info) {
                COLDIGO.exibirAviso("Erro ao buscar as marcas: " + info.status + " - " + info.statusText)

                $("#selMarca").html("")
                let option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Erro ao carregar marcas!")
                $("#selMarca").append(option)
                $("#selMarca").addClass("aviso")
            }
        })
    }
    COLDIGO.produto.carregarMarcas()
})