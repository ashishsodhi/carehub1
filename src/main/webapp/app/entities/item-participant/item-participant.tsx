import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './item-participant.reducer';
import { IItemParticipant } from 'app/shared/model/item-participant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IItemParticipantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ItemParticipant extends React.Component<IItemParticipantProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { itemParticipantList, match } = this.props;
    return (
      <div>
        <h2 id="item-participant-heading">
          Item Participants
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Item Participant
          </Link>
        </h2>
        <div className="table-responsive">
          {itemParticipantList && itemParticipantList.length > 0 ? (
            <Table responsive aria-describedby="item-participant-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Permission</th>
                  <th>When Created</th>
                  <th>Project Participant</th>
                  <th>Recipient Item</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {itemParticipantList.map((itemParticipant, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${itemParticipant.id}`} color="link" size="sm">
                        {itemParticipant.id}
                      </Button>
                    </td>
                    <td>{itemParticipant.permission}</td>
                    <td>
                      <TextFormat type="date" value={itemParticipant.whenCreated} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {itemParticipant.projectParticipant ? (
                        <Link to={`project-participant/${itemParticipant.projectParticipant.id}`}>
                          {itemParticipant.projectParticipant.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {itemParticipant.recipientItem ? (
                        <Link to={`recipient-item/${itemParticipant.recipientItem.id}`}>{itemParticipant.recipientItem.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${itemParticipant.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${itemParticipant.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${itemParticipant.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Item Participants found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ itemParticipant }: IRootState) => ({
  itemParticipantList: itemParticipant.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ItemParticipant);
