COLDIGO.marca = new Object();

$(document).ready(function() {
  const path = COLDIGO.PATH;

  COLDIGO.marca.cadastrar = function() {
    let marca = new Object()
    marca.nome = document.frmAddMarca.nome.value

    if (COLDIGO.empty(marca.nome)) {
      COLDIGO.exibirAviso("Digite um nome para a marca!")
    } else {
      $.ajax({
        type: "POST",
        url: path + "/marca/inserir",
        data:JSON.stringify(marca),
        success: function (msg) {
          COLDIGO.exibirAviso(msg);
          $("#addMarca").trigger("reset");
          COLDIGO.marca.buscarPorNome();
        },
        error: function (info) {
          COLDIGO.exibirAviso("Erro ao cadastrar uma nova marca: " + info.status + " - " + info.statusText);
        }
      })
    }
  }

  COLDIGO.marca.buscarPorNome = function() {
    let valorBusca = $("#campoBuscaMarca").val()

    $.ajax({
      type: "GET",
      url: path + "/marca/buscar/nome",
      data: "valorBusca=" + valorBusca, 
      success: function(dados) {
        dados = JSON.parse(dados);
        $('#listaMarcas').html(COLDIGO.marca.exibir(dados));
      },
      error: function(info) {
        COLDIGO.exibirAviso("Erro ao consultar os contatos: " + info.status + " - " + info.statusText);
      }
    })
  }

  COLDIGO.marca.excluir = function(id) {
    $.ajax({
      type: "DELETE",
      url: COLDIGO.PATH + "/marca/excluir/" + id,
      success: function(msg) {
        COLDIGO.exibirAviso(msg);
        COLDIGO.marca.buscarPorNome();
      },
      error: function(info) {
        COLDIGO.exibirAviso(`Erro ao excluir marca ${info.status} - ${info.statusText}`);
      }
    });
  }

  COLDIGO.marca.editar = function() {
    const { idMarca, nome } = document.frmEditarMarca;

    let marca = {};
    marca.id = parseInt(idMarca.value);
    marca.nome = nome.value;
    
    console.log(marca);
    
    $.ajax({
      type: "PUT",
      url: path + "/marca/alterar",
      data: JSON.stringify(marca),
      success: function(msg) {
        COLDIGO.exibirAviso(msg);
        COLDIGO.marca.buscarPorNome();
        $("#modalEditarMarca").dialog("close");
      },
      error: function(info) {
        COLDIGO.exibirAviso(`Erro ao editar marca: ${info.status} - ${info.statusText}`);
      }
    });
  }

  COLDIGO.marca.exibir = function(listaDeMarcas) {
    let tabela = `
      <table>
        <tr>
          <th>Nome</th>
          <th class='acoes'>Ações</th>
        </tr>
    `;

      if (listaDeMarcas != undefined && listaDeMarcas.length > 0) {
          for (marca of listaDeMarcas) {
              tabela += `
                  <tr>
                      <td>${marca.nome}</td>
                      <td>
                          <a onclick='COLDIGO.marca.exibirEdicao(${marca.id})'><img src='/coldigoGeladeiras/imgs/edit.png' alt='Editar registro'></a>
                          <a onclick='COLDIGO.marca.excluir(${marca.id})'><img src='/coldigoGeladeiras/imgs/delete.png' alt='Excluir registro'></a>
                      </td>
                  </tr>
              `;
          }
      } else if (listaDeMarcas == "") {
          tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
      }

      tabela += "</table>"
        
      return tabela;
  }

  COLDIGO.marca.exibirEdicao = function(id){
    $.ajax({
        type: "GET",
        url: COLDIGO.PATH + "/marca/buscarPorId",
        data: { id },
        success: function(marca) {
            document.frmEditarMarca.idMarca.value = marca.id;
            document.frmEditarMarca.nome.value = marca.nome;

            let modalEditarMarca = {
                title: "Editar Marca",
                height: 400,
                width: 550,
                modal: true,
                buttons: {
                    "Salvar": function() {
                        COLDIGO.marca.editar();
                    },
                    "Cancelar": function() {
                        $(this).dialog("close");
                    }
                },
                close: function() {

                }
            };

            $("#modalEditarMarca").dialog(modalEditarMarca);
        },
        error: function(info) {
            COLDIGO.exibirAviso(`Erro ao buscar marca para edição: ${info.status} - ${info.statusText}`);
        }
    });
  }

  COLDIGO.marca.buscarPorNome();
});