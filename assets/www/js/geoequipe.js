var AppConfig = {
    getUserId: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "getUserId", []);
    }
   ,setUserId: function (userId) {
      return cordova.exec(null, null, "AppConfig", "setUserId", [userId]);
    }
   ,getEnviaSinal: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "getEnviaSinal", []);
    }
   ,setEnviaSinalOn: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "setEnviaSinalOn", []);
    }
   ,setEnviaSinalOff: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "setEnviaSinalOff", []);
    }
};

function login() {
  $.ajax({type: 'GET'
         ,url: 'http://geoequipe.aws.af.cm/login/' + $("#usuario").val() + '/' + $("#senha").val()
         ,dataType: 'json'
         ,success: function(data) {
                     if (data) {
                       if (data.erro && data.erro.length > 0) {
                         alert(data.erro);
                       } else {
                         if (data.id_usuario && data.id_usuario.length > 0) {
                           // save in local storage
                           AppConfig.setUserId(data.id_usuario);
                           // perform page transition 
                           $.mobile.changePage("lista_tarefas.html");
                         }
                       }
                     } else {
                       alert("Erro ao fazer login!");
                     }
          }
         ,error: function (xhr, ajaxOptions, thrownError) {
              alert("Erro ao fazer login: " + xhr.status + "\n" +
                     "Mensagem: " + xhr.statusText + "\n" +
                     "Resposta: " + xhr.responseText + "\n" + thrownError);
          }
      });
}

function logout() {
  AppConfig.setUserId("");
  $.mobile.changePage("index.html");
}

function trocaEnvioSinal() {
  
}

$(document).on("pageshow", "#configuracoes", function() {
  $("#enviaSinal").on("change", function() {
    if ($(this).val() == "on")
      AppConfig.setEnviaSinalOn(null, null);
    else
      AppConfig.setEnviaSinalOff(null, null);
  });
  AppConfig.getEnviaSinal(function(retorno) {
    $('#enviaSinal').val(retorno);
    $('#enviaSinal').slider('refresh');
  }, function(){});
});

$(document).on("pageshow", "#lista_tarefas", function() {
  $("#tarefas").html(fnc_tarefa()).trigger('create');
});

function fnc_tarefa() {
  var v_tarefas =   { "tarefas":[{
              "id_tarefa":"1",
              "descricao":"Esta tudo dando errado, Dona francisca nao sabe mais o que fazer.",
              "local":"Loja da Francisca",
              "apontamento":"O equipamento estava fora da tomada. Mulher burra pra caramba.",
              "coord":
              {
                "lat":-23.123,
                "lng":-46.123
              }
            },
            {
              "id_tarefa":"2",
              "descricao":"A independencia da comissão criada nesta sexta-feira (9) para investigar o suposto cartel envolvendo o metro e os trens metropolitanos de Sao Paulo foi questionada por integrantes do grupo ja anunciado.",
              "local":"Pizzaria",
              "apontamento":"",
              "coord":
              {
                "lat":-23.456,
                "lng":-46.456
              }
            },
            {
              "id_tarefa":"2",
              "descricao":"Antes do anuncio, Claudio Abramo, diretor-executivo da Transparencia Brasil, um dos orgaos da comissão, comemorou a independencia da comissão aparentemente sem saber que ela seria encabecada pela Corregedoria. O governo do Estado toma uma medida que nao envolve participantes do governo. Essa comissão não tem representantes do governo.",
              "local":"Borracharia Barbosa",
              "apontamento":"",
              "coord":
              {
                "lat":-23.456,
                "lng":-46.456
              }
            },
            {
              "id_tarefa":"3",
              "descricao":"No entanto, depois de Ungaro ser anunciado, Abramo confrontou o proprio governador sobre a independencia do grupo. Ironico, o diretor-executivo da Transparencia Brasil perguntou se nao seriam so grupos da sociedade civil que participariam da comissa",
              "local":"Pizzaria Boi na Brasa",
              "apontamento":"",
              "coord":
              {
                "lat":-23.798,
                "lng":-46.789
              }
            }
            ],
            "erro":"Erro ao carregar tarefas."
          };
        
  listatarefas = '';
  
  for (i=0; i<v_tarefas.tarefas.length; i++) {
  
    listatarefas += '<div data-role="collapsible" data-collapsed="false">' +
                '<h3>' +
                    v_tarefas.tarefas[i].local +
                '</h3>' +
        '<font size="2px">' + v_tarefas.tarefas[i].descricao + '</font>' +
        '<input type="hidden" name="id_tarefa" id="id_tarefa" value="' + v_tarefas.tarefas[i].id_tarefa + '"' +
        '<br>' +
        '<textarea name="" id="apontamento" placeholder="Apontamento" cols="200">' + v_tarefas.tarefas[i].apontamento + '</textarea>' +
        '<div class="ui-grid-a">' +
                    '<div class="ui-block-a">' +
            '<input type="submit" data-inline="true" data-theme="b" data-icon="check" data-iconpos="left" value="Concluir" data-mini="true">' +
          '</div>' +
          '<div class="ui-block-b">' +
            '<a href="geo:' + v_tarefas.tarefas[i].coord.lat + ',' + v_tarefas.tarefas[i].coord.lng + '">' +
              '<div style=" text-align:right">' +
                '<img style="width: 40px; height: 40px" src="img/google-maps-icone.png">' +
              '</div>' +
            '</a>' +
          '</div>' +
        '</div>' +
            '</div>';
    
  }
  return listatarefas;
}