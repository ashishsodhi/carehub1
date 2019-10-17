import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './concerns.reducer';
import { IConcerns } from 'app/shared/model/concerns.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConcernsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Concerns extends React.Component<IConcernsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { concernsList, match } = this.props;
    return (
      <div>
        <h2 id="concerns-heading">
          Concerns
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Concerns
          </Link>
        </h2>
        <div className="table-responsive">
          {concernsList && concernsList.length > 0 ? (
            <Table responsive aria-describedby="concerns-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Description</th>
                  <th>Status</th>
                  <th>When Created</th>
                  <th>Recipient</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {concernsList.map((concerns, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${concerns.id}`} color="link" size="sm">
                        {concerns.id}
                      </Button>
                    </td>
                    <td>{concerns.description}</td>
                    <td>{concerns.status}</td>
                    <td>
                      <TextFormat type="date" value={concerns.whenCreated} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{concerns.recipient ? <Link to={`recipient/${concerns.recipient.id}`}>{concerns.recipient.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${concerns.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${concerns.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${concerns.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Concerns found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ concerns }: IRootState) => ({
  concernsList: concerns.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Concerns);
