import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './responsibilities.reducer';
import { IResponsibilities } from 'app/shared/model/responsibilities.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResponsibilitiesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Responsibilities extends React.Component<IResponsibilitiesProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { responsibilitiesList, match } = this.props;
    return (
      <div>
        <h2 id="responsibilities-heading">
          Responsibilities
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Responsibilities
          </Link>
        </h2>
        <div className="table-responsive">
          {responsibilitiesList && responsibilitiesList.length > 0 ? (
            <Table responsive aria-describedby="responsibilities-heading">
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
                {responsibilitiesList.map((responsibilities, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${responsibilities.id}`} color="link" size="sm">
                        {responsibilities.id}
                      </Button>
                    </td>
                    <td>{responsibilities.description}</td>
                    <td>{responsibilities.status}</td>
                    <td>
                      <TextFormat type="date" value={responsibilities.whenCreated} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {responsibilities.recipient ? (
                        <Link to={`recipient/${responsibilities.recipient.id}`}>{responsibilities.recipient.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${responsibilities.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${responsibilities.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${responsibilities.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Responsibilities found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ responsibilities }: IRootState) => ({
  responsibilitiesList: responsibilities.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Responsibilities);
