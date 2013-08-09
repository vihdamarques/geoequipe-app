function fnc_tarefa() {

var v_tarefas = 	{	"tarefas":[{
						"id_tarefa":"1",
						"descricao":"Est� tudo dando errado, Dona francisca n�o sabe mais o que fazer.",
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
						"descricao":"A independ�ncia da comiss�o criada nesta sexta-feira (9) para investigar o suposto cartel envolvendo o metr� e os trens metropolitanos de S�o Paulo foi questionada por integrantes do grupo j� anunciado. Montada para ser uma comiss�o externa, formada por �rg�os da sociedade civil, ela ser� regida pela Corregedoria Geral de Administra��o, segundo anunciou hoje o Governo de S�o Paulo.",
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
						"descricao":"Antes do an�ncio, Claudio Abramo, diretor-executivo da Transpar�ncia Brasil, um dos �rg�os da comiss�o, comemorou a independ�ncia da comiss�o aparentemente sem saber que ela seria encabe�ada pela Corregedoria. O governo do Estado toma uma medida que n�o envolve participantes do governo. Essa comiss�o n�o tem representantes do governo. Ent�o, a influ�ncia que o governo possa ter nessas decis�es n�o existe dentro desse contexto, disse ele. ",
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
						"descricao":"No entanto, depois de �ngaro ser anunciado, Abramo confrontou o pr�prio governador sobre a independ�ncia do grupo. Ir�nico, o diretor-executivo da Transpar�ncia Brasil perguntou se n�o seriam s� grupos da sociedade civil que participariam da comiss�o e questionou a presen�a do corregedor, que poderia filtrar as informa��es passadas ao grupo segundo interesse do governo.",
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
								'<img style="width: 40px; height: 40px" src="img\google-maps-icone.png">' +
							'</div>' +
						'</a>' +
					'</div>' +
				'</div>' +
            '</div>';
		
	}
	return listatarefas;
}