import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './message-item.reducer';
import { IMessageItem } from 'app/shared/model/message-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMessageItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class MessageItem extends React.Component<IMessageItemProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { messageItemList, match } = this.props;
    return (
      <div>
        <h2 id="message-item-heading">
          Message Items
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Message Item
          </Link>
        </h2>
        <div className="table-responsive">
          {messageItemList && messageItemList.length > 0 ? (
            <Table responsive aria-describedby="message-item-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>When Created</th>
                  <th>Message</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {messageItemList.map((messageItem, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${messageItem.id}`} color="link" size="sm">
                        {messageItem.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={messageItem.whenCreated} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{messageItem.message ? <Link to={`message/${messageItem.message.id}`}>{messageItem.message.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${messageItem.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${messageItem.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${messageItem.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Message Items found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ messageItem }: IRootState) => ({
  messageItemList: messageItem.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MessageItem);
