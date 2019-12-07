COLDIGO.produto = new Object()
const path = COLDIGO.PATH

$(document).ready(function() {
    COLDIGO.produto.carregarMarcas = function() {
        $.ajax({
            type: "GET",
            url: path + "/marca/buscar",
            success: function(marcas) {
                if (!COLDIGO.empty(marcas)) {
                    $("#marcas_id").html("")

                    let option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Escolha")
                    $("#marcas_id").append(option)

                    for (const i in marcas) {
                        let option = document.createElement("option")
                        option.setAttribute("value", marcas[i].id)
                        option.innerHTML = (marcas[i].nome)
                        $("#marcas_id").append(option)
                    }
                } else {
                    $("#marcas_id").html("")

                    let option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Cadastre uma marca primeiro!")
                    $("#marcas_id").append(option)
                    $("#marcas_id").addClass("aviso")
                }
            },
            error: function(info) {
                COLDIGO.exibirAviso("Erro ao buscar as marcas: " + info.status + " - " + info.statusText)

                $("#marcas_id").html("")
                let option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Erro ao carregar marcas!")
                $("#marcas_id").append(option)
                $("#marcas_id").addClass("aviso")
            }
        })
    }
    COLDIGO.produto.carregarMarcas()

    COLDIGO.produto.cadastrar = function() {
        let produto = new Object()
        produto.categoria = document.frmAddProduto.categoria.value
        produto.marcas_id = document.frmAddProduto.marcas_id.value
        produto.modelo = document.frmAddProduto.modelo.value
        produto.capacidade = document.frmAddProduto.capacidade.value
        produto.valor = document.frmAddProduto.valor.value

        if (COLDIGO.empty(produto.categoria)) {
            COLDIGO.exibirAviso("Selecione uma categoria!")
        } else if (COLDIGO.empty(produto.marcas_id)) {
            COLDIGO.exibirAviso("Selecione uma marca!")
        } else if (COLDIGO.empty(produto.modelo)) {
            COLDIGO.exibirAviso("Informe o modelo!")
        } else if (COLDIGO.empty(produto.capacidade)) {
            COLDIGO.exibirAviso("Informe a capacidade!")
        } else if (COLDIGO.empty(produto.valor)) {
            COLDIGO.exibirAviso("Informe o valor!")
        } else {
            $.ajax({
                type: "POST",
                url: path + "/produto/inserir",
                data:JSON.stringify(produto),
                success: function (msg) {
                    COLDIGO.exibirAviso(msg)
                    $("#addProduto").trigger("reset")
                },
                error: function (info) {
                    COLDIGO.exibirAviso("Erro ao cadastrar um novo produto: " + info.status + " - " + info.statusText)
                }
            })
        }
    }
})