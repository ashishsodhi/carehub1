import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './recipient.reducer';
import { IRecipient } from 'app/shared/model/recipient.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Recipient extends React.Component<IRecipientProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { recipientList, match } = this.props;
    return (
      <div>
        <h2 id="recipient-heading">
          Recipients
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Recipient
          </Link>
        </h2>
        <div className="table-responsive">
          {recipientList && recipientList.length > 0 ? (
            <Table responsive aria-describedby="recipient-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Relationship To You</th>
                  <th>Status</th>
                  <th>Status TLM</th>
                  <th>When Created</th>
                  <th>Project</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {recipientList.map((recipient, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${recipient.id}`} color="link" size="sm">
                        {recipient.id}
                      </Button>
                    </td>
                    <td>{recipient.relationshipToYou}</td>
                    <td>{recipient.status}</td>
                    <td>
                      <TextFormat type="date" value={recipient.statusTLM} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={recipient.whenCreated} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{recipient.project ? <Link to={`project/${recipient.project.id}`}>{recipient.project.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${recipient.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${recipient.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${recipient.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Recipients found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ recipient }: IRootState) => ({
  recipientList: recipient.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Recipient);
