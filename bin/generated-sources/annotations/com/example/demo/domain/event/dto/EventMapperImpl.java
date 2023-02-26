package com.example.demo.domain.event.dto;

import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.dto.AuthorityDTO;
import com.example.demo.domain.event.Event;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.dto.RoleDTO;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.dto.UserDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-25T14:49:36+0100",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230213-1046, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event fromDTO(EventDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Event event = new Event();

        event.setId( dto.getId() );
        event.setDescription( dto.getDescription() );
        event.setEndDate( dto.getEndDate() );
        event.setEventName( dto.getEventName() );
        event.setEventOwner( userDTOToUser( dto.getEventOwner() ) );
        event.setLocation( dto.getLocation() );
        event.setParticipantsLimit( dto.getParticipantsLimit() );
        event.setStartDate( dto.getStartDate() );

        return event;
    }

    @Override
    public List<Event> fromDTOs(List<EventDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Event> list = new ArrayList<Event>( dtos.size() );
        for ( EventDTO eventDTO : dtos ) {
            list.add( fromDTO( eventDTO ) );
        }

        return list;
    }

    @Override
    public Set<Event> fromDTOs(Set<EventDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<Event> set = new LinkedHashSet<Event>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( EventDTO eventDTO : dtos ) {
            set.add( fromDTO( eventDTO ) );
        }

        return set;
    }

    @Override
    public EventDTO toDTO(Event BO) {
        if ( BO == null ) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();

        eventDTO.setId( BO.getId() );
        eventDTO.setDescription( BO.getDescription() );
        eventDTO.setEndDate( BO.getEndDate() );
        eventDTO.setEventName( BO.getEventName() );
        eventDTO.setEventOwner( userToUserDTO( BO.getEventOwner() ) );
        eventDTO.setLocation( BO.getLocation() );
        eventDTO.setParticipantsLimit( BO.getParticipantsLimit() );
        eventDTO.setStartDate( BO.getStartDate() );

        return eventDTO;
    }

    @Override
    public List<EventDTO> toDTOs(List<Event> BOs) {
        if ( BOs == null ) {
            return null;
        }

        List<EventDTO> list = new ArrayList<EventDTO>( BOs.size() );
        for ( Event event : BOs ) {
            list.add( toDTO( event ) );
        }

        return list;
    }

    @Override
    public Set<EventDTO> toDTOs(Set<Event> BOs) {
        if ( BOs == null ) {
            return null;
        }

        Set<EventDTO> set = new LinkedHashSet<EventDTO>( Math.max( (int) ( BOs.size() / .75f ) + 1, 16 ) );
        for ( Event event : BOs ) {
            set.add( toDTO( event ) );
        }

        return set;
    }

    protected Authority authorityDTOToAuthority(AuthorityDTO authorityDTO) {
        if ( authorityDTO == null ) {
            return null;
        }

        Authority authority = new Authority();

        authority.setId( authorityDTO.getId() );
        authority.setName( authorityDTO.getName() );

        return authority;
    }

    protected Set<Authority> authorityDTOSetToAuthoritySet(Set<AuthorityDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Authority> set1 = new LinkedHashSet<Authority>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDTO authorityDTO : set ) {
            set1.add( authorityDTOToAuthority( authorityDTO ) );
        }

        return set1;
    }

    protected Role roleDTOToRole(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleDTO.getId() );
        role.setName( roleDTO.getName() );
        role.setAuthorities( authorityDTOSetToAuthoritySet( roleDTO.getAuthorities() ) );

        return role;
    }

    protected Set<Role> roleDTOSetToRoleSet(Set<RoleDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new LinkedHashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleDTO roleDTO : set ) {
            set1.add( roleDTOToRole( roleDTO ) );
        }

        return set1;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setFirstName( userDTO.getFirstName() );
        user.setLastName( userDTO.getLastName() );
        user.setEmail( userDTO.getEmail() );
        user.setRoles( roleDTOSetToRoleSet( userDTO.getRoles() ) );

        return user;
    }

    protected AuthorityDTO authorityToAuthorityDTO(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        AuthorityDTO authorityDTO = new AuthorityDTO();

        authorityDTO.setId( authority.getId() );
        authorityDTO.setName( authority.getName() );

        return authorityDTO;
    }

    protected Set<AuthorityDTO> authoritySetToAuthorityDTOSet(Set<Authority> set) {
        if ( set == null ) {
            return null;
        }

        Set<AuthorityDTO> set1 = new LinkedHashSet<AuthorityDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Authority authority : set ) {
            set1.add( authorityToAuthorityDTO( authority ) );
        }

        return set1;
    }

    protected RoleDTO roleToRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId( role.getId() );
        roleDTO.setName( role.getName() );
        roleDTO.setAuthorities( authoritySetToAuthorityDTOSet( role.getAuthorities() ) );

        return roleDTO;
    }

    protected Set<RoleDTO> roleSetToRoleDTOSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleDTO> set1 = new LinkedHashSet<RoleDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleDTO( role ) );
        }

        return set1;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setRoles( roleSetToRoleDTOSet( user.getRoles() ) );

        return userDTO;
    }
}
