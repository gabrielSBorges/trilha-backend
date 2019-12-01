COLDIGO.produto = new Object()

$(document).ready(function() {
    COLDIGO.produto.carregarMarcas = function() {
        $.ajax({
            type: "GET",
            url: "/ProjetoTrilhaWeb/rest/marca/buscar",
            success: function() {
                
            },
            error: function() {
                
            }
        })
    }
})