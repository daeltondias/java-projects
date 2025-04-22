<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="taglib"
uri="/WEB-INF/tlds/taglib" %>

<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <title>Detalhes do Projeto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="/css/style.css" />
    <!-- Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
      crossorigin="anonymous"
    />
  </head>
  <body>
    <div class="container mt-5">
      <a
        href="/projects"
        class="float-start mt-4 fs-5 text-decoration-none text-black user-select-none hover-opacity transition"
        style="cursor: pointer"
      >
        <img src="/icons/arrow-back.svg" alt="Voltar" />
        Voltar
      </a>
      <h1 class="text-center mb-4">Detalhes do Projeto</h1>
      <div class="card">
        <div class="card-body px-5 py-4">
          <h5 class="card-title text-primary fw-bold mt-3 mb-4">
            ${project.nome}
          </h5>
          <c:if test="${not empty project.descricao}">
            <p><strong>Descrição:</strong> ${project.descricao}</p>
          </c:if>
          <div class="row">
            <p class="col-6">
              <strong>Gerente responsável:</strong> ${project.gerente.nome}
            </p>
            <p class="col-6">
              <strong>Data de início:</strong>
              <taglib:formatLocalDate
                value="${project.data_inicio}"
                pattern="dd/MM/yyyy"
              />
            </p>
            <p class="col-6">
              <strong>Previsão de término:</strong>
              <taglib:formatLocalDate
                value="${project.data_previsao_fim}"
                pattern="dd/MM/yyyy"
              />
            </p>
            <p class="col-6">
              <strong>Data de término:</strong>
              <taglib:formatLocalDate
                value="${project.data_fim}"
                pattern="dd/MM/yyyy"
              />
            </p>
            <p class="col-6">
              <strong>Orçamento:</strong>
              <fmt:formatNumber
                value="${project.orcamento}"
                currencySymbol="R$"
                type="currency"
              />
            </p>
            <p class="col-6">
              <strong>Status:</strong>
              <!-- prettier-ignore -->
              <span
                style="padding: 1px 10px; font-weight: 500;"
                class="rounded-pill ${
                  project.status eq 'em análise' ? 'text-bg-warning' :
                  project.status eq 'análise realizada' ? 'text-bg-info' :
                  project.status eq 'análise aprovada' ? 'text-bg-primary' :
                  project.status eq 'iniciado' ? 'text-bg-secondary' :
                  project.status eq 'planejado' ? 'text-bg-dark' :
                  project.status eq 'em andamento' ? 'text-bg-light border' :
                  project.status eq 'encerrado' ? 'text-bg-success' :
                  project.status eq 'cancelado' ? 'text-bg-danger' :
                  'text-bg-secondary'
                }"
              >
                <c:choose>
                  <c:when test="${project.status eq 'encerrado'}">
                    concluído
                  </c:when>
                  <c:otherwise>
                    ${project.status}
                  </c:otherwise>
                </c:choose>
              </span>
            </p>
            <p class="col-6">
              <strong>Classificação:</strong>
              ${project.risco}
            </p>
          </div>
          <p><strong>Membros</strong></p>
          <div class="d-flex flex-wrap gap-3" style="margin-top: -5px">
            <c:choose>
              <c:when test="${not empty project.membros}">
                <c:forEach var="membro" items="${project.membros}">
                  <div class="member-card">
                    <div class="icon">
                      <img src="/icons/user.svg" alt="" />
                    </div>
                    <div class="text">
                      <p class="fw-normal" style="margin-bottom: -6px">
                        ${membro.pessoa.nome}
                      </p>
                      <small class="text-primary">
                        Funcionário
                        <c:if test="${membro.pessoa.gerente}"> (Gerente)</c:if>
                      </small>
                    </div>
                    <c:if
                      test="${project.status != 'encerrado' && project.status != 'cancelado'}"
                    >
                      <button
                        type="button"
                        class="btn-close btn-hover transition p-2 rounded-circle"
                        style="font-size: 12px"
                        aria-label="Close"
                        data-bs-placement="top"
                        data-bs-toggle="tooltip"
                        data-bs-title="Remover membro"
                        onclick="removeMemberToProject({
                          idpessoa: `${membro.pessoa.id}`,
                          idprojeto: `${project.id}`
                        })"
                      ></button>
                    </c:if>
                  </div>
                </c:forEach>
              </c:when>
              <c:otherwise>Nenhum membro atribuído</c:otherwise>
            </c:choose>
          </div>
          <c:if
            test="${project.status != 'encerrado' && project.status != 'cancelado'}"
          >
            <div class="dropup dropup-center mt-3">
              <button
                class="btn btn-light"
                data-bs-auto-close="outside"
                data-bs-toggle="dropdown"
                data-bs-offset="0,8"
              >
                <img src="/icons/plus.svg" alt="" />
                Adicionar membro
              </button>
              <div class="dropdown-menu shadow search-container">
                <div class="input-group px-3 py-2">
                  <span class="input-group-text" id="basic-addon1">
                    <img src="/icons/search.svg" alt="" />
                  </span>
                  <input
                    type="text"
                    class="form-control search-input"
                    placeholder="Buscar membro"
                  />
                </div>
                <div class="overflow-y-auto" style="max-height: 200px">
                  <c:choose>
                    <c:when test="${not empty members}">
                      <c:forEach var="member" items="${members}">
                        <div
                          class="search-item"
                          onclick="addMemberToProject({
                            idpessoa: `${member.id}`,
                            idprojeto: `${project.id}`,
                          })"
                          data-bs-title="Adicionar membro"
                          data-bs-placement="right"
                          data-bs-toggle="tooltip"
                        >
                          <div
                            class="d-flex justify-content-between align-items-center border-bottom px-3 py-1 btn btn-light rounded-0 text-start"
                          >
                            <div>
                              <p class="fw-normal" style="margin-bottom: -6px">
                                ${member.nome}
                              </p>
                              <small class="text-primary">
                                Funcionário
                                <c:if test="${member.gerente}"> (Gerente)</c:if>
                              </small>
                            </div>
                            <img
                              src="/icons/plus.svg"
                              style="margin-right: -10px"
                              alt=""
                            />
                          </div>
                        </div>
                      </c:forEach>
                      <p class="search-not-found text-center small py-4">
                        Nenhum membro encontrado.
                      </p>
                    </c:when>
                    <c:otherwise>
                      <p class="text-center small py-4">
                        Nenhum membro encontrado.
                      </p>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </div>
          </c:if>
        </div>
        <c:if test="${project.status != 'encerrado'}">
          <div class="card-footer d-flex justify-content-end gap-2">
            <c:if test="${project.status != 'cancelado'}">
              <!-- prettier-ignore  -->
              <button
                onclick="advanceProject('${project.id}')"
                class="btn btn-sm btn-success px-3 fw-medium"
              >
                <!-- prettier-ignore -->
                <c:choose>
                  <c:when test="${project.status eq 'em análise'}">Finalizar análise</c:when>
                  <c:when test="${project.status eq 'análise realizada'}">Aprovar análise</c:when>
                  <c:when test="${project.status eq 'análise aprovada'}">Iniciar planejamento</c:when>
                  <c:when test="${project.status eq 'iniciado'}">Finalizar planejamento</c:when>
                  <c:when test="${project.status eq 'planejado'}">Iniciar desenvolvimento</c:when>
                  <c:when test="${project.status eq 'em andamento'}">Encerrar desenvolvimento</c:when>
                </c:choose>
              </button>
            </c:if>
            <c:choose>
              <c:when test="${project.status == 'cancelado'}">
                <div class="dropup dropup-center">
                  <button
                    class="btn btn-danger btn-sm px-3"
                    data-bs-auto-close="true"
                    data-bs-toggle="dropdown"
                    data-bs-offset="0,8"
                  >
                    Deletar projeto
                  </button>
                  <div class="dropdown-menu p-3 shadow" style="width: 300px">
                    <p class="fw-medium m-0">
                      Deseja mesmo deletar esse projeto?
                    </p>
                    <p>
                      <small>Não será mais possível recuperá-lo.</small>
                    </p>
                    <div class="row gx-0 gap-2">
                      <button
                        class="col btn btn-danger btn-sm"
                        onclick="deleteProject('${project.id}')"
                      >
                        Sim, deletar
                      </button>
                      <button class="col btn btn-secondary btn-sm">
                        Cancelar
                      </button>
                    </div>
                  </div>
                </div>
              </c:when>
              <c:otherwise>
                <div class="dropup dropup-center">
                  <button
                    class="btn btn-danger btn-sm px-3"
                    data-bs-auto-close="true"
                    data-bs-toggle="dropdown"
                    data-bs-offset="0,8"
                  >
                    Cancelar projeto
                  </button>
                  <div class="dropdown-menu p-3 shadow" style="width: 300px">
                    <c:choose>
                      <c:when test="${project.pode_excluir}">
                        <p class="fw-medium m-0">
                          Deseja mesmo cancelar o projeto?
                        </p>
                        <p>
                          <small>Não será mais possível recuperá-lo.</small>
                        </p>
                        <div class="row gx-0 gap-2">
                          <button
                            class="col btn btn-danger btn-sm"
                            onclick="cancelProject('${project.id}')"
                          >
                            Sim, cancelar
                          </button>
                          <button
                            class="col btn btn-secondary btn-sm dropdown-hide"
                          >
                            Não
                          </button>
                        </div>
                      </c:when>
                      <c:otherwise>
                        <div class="row gx-0 gap-2">
                          <div class="col-auto">
                            <img src="/icons/warning.svg" alt="Aviso" />
                          </div>
                          <div class="col">
                            <p class="m-0">
                              <small class="fw-semibold">
                                <c:choose>
                                  <c:when
                                    test="${project.status eq 'iniciado'}"
                                  >
                                    Não é possível cancelar, pois o projeto já
                                    foi iniciado.
                                  </c:when>
                                  <c:when
                                    test="${project.status eq 'em andamento'}"
                                  >
                                    Não é possível cancelar, pois o projeto está
                                    em andamento.
                                  </c:when>
                                </c:choose>
                              </small>
                            </p>
                          </div>
                        </div>
                      </c:otherwise>
                    </c:choose>
                  </div>
                </div>
              </c:otherwise>
            </c:choose>
            <c:if test="${project.status != 'cancelado'}">
              <button
                class="btn btn-warning btn-sm px-3"
                data-bs-toggle="modal"
                data-bs-target="#modal-project-form"
                data-id="${project.id}"
                data-nome="${project.nome}"
                data-data_inicio="${project.data_inicio}"
                data-data_previsao_fim="${project.data_previsao_fim}"
                data-descricao="${project.descricao}"
                data-orcamento="${project.orcamento}"
                data-idgerente="${project.gerente.id}"
                data-risco="${project.risco}"
              >
                Editar informações do projeto
              </button>
            </c:if>
          </div>
        </c:if>
      </div>
    </div>

    <!-- Modal -->
    <div
      tabindex="-1"
      aria-hidden="true"
      class="modal fade"
      id="modal-project-form"
    >
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <form id="project-form" class="w-100 needs-validation" novalidate>
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5">Editar projeto</h1>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <div class="form-floating mb-3">
                <input
                  type="text"
                  class="form-control"
                  id="nome"
                  placeholder="Nome"
                  name="nome"
                  required
                />
                <label for="nome" class="fw-medium">Nome do projeto</label>
                <div class="invalid-feedback">Campo obrigatório</div>
              </div>
              <div class="form-floating mb-3">
                <select
                  class="form-select"
                  id="idgerente"
                  name="idgerente"
                  required
                >
                  <option value="" selected hidden>Selecione</option>
                  <c:forEach var="manager" items="${managers}">
                    <option value="${manager.id}">
                      ${manager.nome} - ${manager.cpf}
                    </option>
                  </c:forEach>
                </select>
                <label for="idgerente" class="fw-medium">Gerente</label>
                <div class="invalid-feedback">Campo obrigatório</div>
              </div>
              <div class="form-floating mb-3">
                <select class="form-select" id="risco" name="risco" required>
                  <option value="" selected hidden>Selecione</option>
                  <option value="Baixo risco">Baixo risco</option>
                  <option value="Médio risco">Médio risco</option>
                  <option value="Alto risco">Alto risco</option>
                </select>
                <label for="risco" class="fw-medium">Classificação</label>
                <div class="invalid-feedback">Campo obrigatório</div>
              </div>
              <div class="row gx-0 gap-3">
                <div class="col">
                  <div class="form-floating mb-3">
                    <input
                      type="date"
                      class="form-control"
                      id="data_inicio"
                      placeholder="Data de início"
                      name="data_inicio"
                      required
                    />
                    <label for="data_inicio" class="fw-medium"
                      >Data de início</label
                    >
                    <div class="invalid-feedback">Campo obrigatório</div>
                  </div>
                </div>
                <div class="col">
                  <div class="form-floating mb-3">
                    <input
                      type="date"
                      class="form-control"
                      id="data_previsao_fim"
                      placeholder="Previsão de término"
                      name="data_previsao_fim"
                      required
                    />
                    <label for="data_previsao_fim" class="fw-medium"
                      >Previsão de término</label
                    >
                    <div class="invalid-feedback">Campo obrigatório</div>
                  </div>
                </div>
              </div>
              <div class="input-group mb-3 has-validation">
                <span
                  class="input-group-text"
                  style="
                    max-height: 58px;
                    border-top-right-radius: 0px;
                    border-bottom-right-radius: 0px;
                  "
                  >R$</span
                >
                <div class="form-floating">
                  <input
                    type="text"
                    id="orcamento"
                    class="form-control"
                    placeholder="Orçamento"
                    pattern="^(?!0,00$).*"
                    data-mask-currency
                    name="orcamento"
                    required
                  />
                  <label for="orcamento" class="fw-medium">Orçamento</label>
                  <div class="invalid-feedback" style="margin-left: -40px">
                    Campo obrigatório
                  </div>
                </div>
              </div>
              <div class="form-floating mb-3">
                <textarea
                  class="form-control"
                  placeholder="Descrição do projeto"
                  style="height: 100px"
                  id="descricao"
                  name="descricao"
                ></textarea>
                <label for="descricao" class="fw-medium"
                  >Descrição do projeto</label
                >
              </div>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Cancelar
              </button>
              <button type="submit" class="btn btn-success" id="button">
                Alterar
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
      crossorigin="anonymous"
    ></script>

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="/js/partials/snippets/converts.js"></script>
    <script src="/js/partials/snippets/tooltip.js"></script>
    <script src="/js/partials/snippets/masks.js"></script>

    <script>
      document.querySelectorAll(".search-input").forEach((input) => {
        input.addEventListener("input", function () {
          const query = this.value.toLowerCase().trim();
          const container = this.closest(".search-container");
          const items = container.querySelectorAll(".search-item");
          const notFound = container.querySelector(".search-not-found");
          let anyFound = false;
          items.forEach((item) => {
            const text = item.textContent.toLowerCase().trim();
            item.style.display = text.includes(query) ? "block" : "none";
            if (text.includes(query)) anyFound = true;
          });
          notFound.style.display = anyFound ? "none" : "block";
        });
      });
    </script>

    <script>
      const modal = document.getElementById("modal-project-form");
      const form = document.getElementById("project-form");

      let projectID;

      // prettier-ignore
      modal.addEventListener("show.bs.modal", function (event) {
        const button = event.relatedTarget;
        const data = button.dataset;
        projectID = data.id;
        document.getElementById("nome").value = data.nome || "";
        document.getElementById("idgerente").value = data.idgerente || "";
        document.getElementById("data_inicio").value = data.data_inicio || "";
        document.getElementById("data_previsao_fim").value = data.data_previsao_fim || "";
        document.getElementById("orcamento").value = floatToCurrency(data.orcamento) || "";
        document.getElementById("descricao").value = data.descricao || "";
        document.getElementById("risco").value = data.risco || "";
        $("#orcamento").trigger("input");
      });

      form.addEventListener("submit", (event) => {
        event.preventDefault();
        if (form.checkValidity()) {
          const formData = new FormData(event.target);
          const data = Object.fromEntries(formData.entries());
          data.orcamento = currencyToFloat(data.orcamento);
          updateProject(data);
        }
      });

      const updateProject = (data) => {
        $.ajax({
          url: "/api/projects/" + projectID,
          method: "PATCH",
          data: JSON.stringify(data),
          headers: {
            "Content-Type": "application/json",
          },
          success: function (response) {
            location.reload();
          },
          error: function () {
            //
          },
        });
      };

      const deleteProject = (projectID) => {
        $.ajax({
          url: "/api/projects/" + projectID,
          method: "DELETE",
          success: function (response) {
            location.href = "/projects";
          },
          error: function () {
            //
          },
        });
      };

      const cancelProject = (projectID) => {
        $.ajax({
          url: "/api/projects/cancel/" + projectID,
          method: "PATCH",
          success: function (response) {
            location.reload();
          },
          error: function () {
            //
          },
        });
      };

      const advanceProject = (projectID) => {
        $.ajax({
          url: "/api/projects/advance/" + projectID,
          method: "PATCH",
          success: function () {
            location.reload();
          },
          error: function () {
            //
          },
        });
      };

      const addMemberToProject = (data) => {
        $.ajax({
          url: "/api/members/add",
          method: "POST",
          data: JSON.stringify(data),
          headers: {
            "Content-Type": "application/json",
          },
          success: function (response) {
            location.reload();
          },
          error: function () {
            //
          },
        });
      };

      const removeMemberToProject = (data) => {
        $.ajax({
          url: "/api/members/remove",
          method: "DELETE",
          data: JSON.stringify(data),
          headers: {
            "Content-Type": "application/json",
          },
          success: function (response) {
            location.reload();
          },
          error: function () {
            //
          },
        });
      };
    </script>
  </body>
</html>
