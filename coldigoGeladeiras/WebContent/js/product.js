COLDIGO.produto = new Object()

$(document).ready(function() {
    COLDIGO.produto.carregarMarcas = function() {
        alert("Tentando buscar marcas")
        $.ajax({
            type: "GET",
            url: "/coldigoGeladeiras/rest/marca/buscar",
            success: function(marcas) {
                alert("Sucesso")
                console.log(marcas)
            },
            error: function(info) {
                alert("Erro")
            }
        })
    }
    COLDIGO.produto.carregarMarcas()
})