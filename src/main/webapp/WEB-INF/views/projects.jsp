<!-- prettier-ignore -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="taglib" uri="/WEB-INF/tlds/taglib" %>

<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <title>Lista de Projetos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
      <h1 class="text-center mb-4">Projetos</h1>
      <div class="d-flex justify-content-end mb-4">
        <!-- Button trigger modal -->
        <button
          type="button"
          class="btn btn-success"
          data-bs-toggle="modal"
          data-bs-target="#modal-project-form"
        >
          Adicionar Novo Projeto
        </button>
      </div>

      <div class="border-bottom">
        <c:choose>
          <c:when test="${not empty projects}">
            <c:forEach var="project" items="${projects}">
              <a href="/projects/${project.id}" class="text-decoration-none">
                <div class="card border-0 bg-hover transition border-top p-2">
                  <div class="card-body">
                    <div class="d-flex align-items-center justify-content-between">
                      <div>
                        <h5 class="card-title text-primary fw-bold mb-3">
                          ${project.nome}
                        </h5>
                        <c:if test="${not empty project.descricao}">
                          <p class="mb-2"><strong>Descrição:</strong> ${project.descricao}</p>
                        </c:if>
                        <div class="d-flex gap-2">
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
                          <!-- prettier-ignore -->
                          <span
                            style="padding: 1px 10px; font-weight: 500;"
                            class="rounded-pill border text-dark ${
                              project.risco eq 'Baixo risco' ? '' :
                              project.risco eq 'Médio risco' ? 'border-warning' :
                              project.risco eq 'Alto risco' ? 'border-danger' :
                              ''
                            }"
                          >
                            ${project.risco.toLowerCase()}
                          </span>
                        </div>
                      </div>
                      <div>
                        <img src="/icons/chevron-right.svg" alt="Chevron">
                      </div>
                    </div>
                  </div>
                </div>
              </a>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <div class="d-flex align-items-center justify-content-center card border-0 border-top py-5">
              <p class="fs-5">Nenhum projeto cadastrado.</p>
            </div>
          </c:otherwise>
        </c:choose>
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
              <h1 class="modal-title fs-5" id="title">Novo projeto</h1>
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
                <select
                  class="form-select"
                  id="risco"
                  name="risco"
                  required
                >
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
                Adicionar
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>

    <!-- Bootstrap JS -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
      crossorigin="anonymous"
    ></script>

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-mask-plugin@1.14.16/dist/jquery.mask.min.js"></script>
    <script src="/js/partials/snippets/validation-form.js"></script>
    <script src="/js/partials/snippets/converts.js"></script>
    <script src="/js/partials/snippets/masks.js"></script>
    <script src="/js/partials/snippets/modal.js"></script>

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
        $("#orcamento").trigger("input");
      });

      form.addEventListener("submit", (event) => {
        event.preventDefault();
        if (form.checkValidity()) {
          const formData = new FormData(event.target);
          const data = Object.fromEntries(formData.entries());
          data.orcamento = currencyToFloat(data.orcamento);
          createProject(data);
        }
      });

      const createProject = (data) => {
        $.ajax({
          url: "/api/projects/new",
          method: "POST",
          data: JSON.stringify(data),
          headers: {
            "Content-Type": "application/json",
          },
          success: function (response) {
            $(modal).modal('hide');
            location.href = "/projects/" + response.id;
          },
          error: function () {
            //
          },
        });
      };
    </script>
  </body>
</html>
