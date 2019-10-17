import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './recipient-item.reducer';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipientItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class RecipientItem extends React.Component<IRecipientItemProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { recipientItemList, match } = this.props;
    return (
      <div>
        <h2 id="recipient-item-heading">
          Recipient Items
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Recipient Item
          </Link>
        </h2>
        <div className="table-responsive">
          {recipientItemList && recipientItemList.length > 0 ? (
            <Table responsive aria-describedby="recipient-item-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Permission To All</th>
                  <th>When Created</th>
                  <th>Task</th>
                  <th>Document</th>
                  <th>Recipient</th>
                  <th>Message Item</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {recipientItemList.map((recipientItem, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${recipientItem.id}`} color="link" size="sm">
                        {recipientItem.id}
                      </Button>
                    </td>
                    <td>{recipientItem.permissionToAll ? 'true' : 'false'}</td>
                    <td>
                      <TextFormat type="date" value={recipientItem.whenCreated} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{recipientItem.task ? <Link to={`task/${recipientItem.task.id}`}>{recipientItem.task.id}</Link> : ''}</td>
                    <td>
                      {recipientItem.document ? <Link to={`document/${recipientItem.document.id}`}>{recipientItem.document.id}</Link> : ''}
                    </td>
                    <td>
                      {recipientItem.recipient ? (
                        <Link to={`recipient/${recipientItem.recipient.id}`}>{recipientItem.recipient.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {recipientItem.messageItem ? (
                        <Link to={`message-item/${recipientItem.messageItem.id}`}>{recipientItem.messageItem.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${recipientItem.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${recipientItem.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${recipientItem.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Recipient Items found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ recipientItem }: IRootState) => ({
  recipientItemList: recipientItem.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RecipientItem);
