function validaFaleConosco() {
    let nome = document.frmfaleconosco.txtnome
    let telefone = document.frmfaleconosco.txtfone
    let email = document.frmfaleconosco.txtemail
    let select = document.frmfaleconosco.selmotivo
    let selectProduto = document.frmfaleconosco.selproduto
    let comentario = document.frmfaleconosco.txtcomentario

    let regexNome = /^[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})+$/
    let regexFone = /^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$/
    let regexEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/


    if (isEmpty(nome.value) || !regexNome.test(nome.value)) {
        alert('Preencha o campo "Nome" corretamente')
        nome.focus()
        return false
    }

    if (isEmpty(telefone.value) || !regexFone.test(telefone.value)) {
        alert('Preencha o campo "Telefone" corretamente!')
        telefone.focus()
        return false
    }

    if (isEmpty(email.value) || !regexEmail.test(email.value)) {
        alert('Preencha o campo "E-mail" corretamente!')
        email.focus()
        return false
    }

    if (isEmpty(select.value)) {
        alert('Escolha um motivo')
        select.focus()
        return false
    } else {
        if (select.value == "info") {
            if (isEmpty(selectProduto.value)) {
                alert('Escolha um produto')
                selectProduto.focus()
                return false
            }
        }
    }

    if (isEmpty(comentario.value)) {
        alert('Preencha o campo "Comentário"')
        comentario.focus()
        return false
    }

    return true
}

function verificaMotivo(motivo) {
    let elemento = document.getElementById("opcaoProduto")

    if (motivo == "info") {
        let select = document.createElement("select")
        select.setAttribute("name", "selproduto")
        
        let values = ["", "freezer", "geladeira"]
        let labels = ["Escolha", "Freezer", "Geladeira"]

        let option
        let texto
        for (var i in values) {
            option = document.createElement("option")
            option.setAttribute("value", values[i])
            texto = document.createTextNode(labels[i])

            option.appendChild(texto)
            select.appendChild(option)
        }
        
        elemento.appendChild(select)
    } else {
        if (elemento.firstChild) {
            elemento.removeChild(elemento.firstChild)
        }
    }
}

function isEmpty(value) {
    if (value == null) {
        return true
    }
    
    if (!isNaN(value)) {
        if (value == 0 || value == '0' || value == 0.00 || value == '0.00') {
            return true
        }
    }

    value = value.replace(/ /g, "")

    if (value.length <= 0) {
        return true
    }

    return false
}

$(document).ready(function() {
    $('header').load("/coldigoGeladeiras/pages/site/general/cabecalho.html")
    $('nav').load('/coldigoGeladeiras/pages/site/general/menu.html')
    $('footer').load('/coldigoGeladeiras/pages/site/general/rodape.html')
});