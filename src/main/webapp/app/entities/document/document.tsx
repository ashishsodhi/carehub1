import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './document.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDocumentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Document extends React.Component<IDocumentProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { documentList, match } = this.props;
    return (
      <div>
        <h2 id="document-heading">
          Documents
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Document
          </Link>
        </h2>
        <div className="table-responsive">
          {documentList && documentList.length > 0 ? (
            <Table responsive aria-describedby="document-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>File Name</th>
                  <th>Extension</th>
                  <th>File Content</th>
                  <th>File Id</th>
                  <th>When Created</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {documentList.map((document, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${document.id}`} color="link" size="sm">
                        {document.id}
                      </Button>
                    </td>
                    <td>{document.fileName}</td>
                    <td>{document.extension}</td>
                    <td>
                      {document.fileContent ? (
                        <div>
                          <a onClick={openFile(document.fileContentContentType, document.fileContent)}>
                            <img
                              src={`data:${document.fileContentContentType};base64,${document.fileContent}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                          <span>
                            {document.fileContentContentType}, {byteSize(document.fileContent)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{document.fileId}</td>
                    <td>
                      <TextFormat type="date" value={document.whenCreated} format={APP_DATE_FORMAT} />
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${document.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${document.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${document.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Documents found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ document }: IRootState) => ({
  documentList: document.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Document);
