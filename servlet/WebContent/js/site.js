function validaFormulario() {
    let nome = document.getElementById('nome');
    let endereco = document.getElementById('endereco');
    let telefone = document.getElementById('telefone');

    if (isNaN(telefone.value) || telefone.value.length < 8 || telefone.value == null) {
        alert("Digite um telefone válido!");
        telefone.focus;
        return false;
    }

    if (nome.value.length < 2 || nome.value == null) {
        alert("Digite um nome válido!");
        nome.focus;
        return false;
    }

    if (endereco.value.length < 10 || endereco.value == null) {
        alert("Digite um endereço válido!");
        endereco.focus;
        return false;
    }
}