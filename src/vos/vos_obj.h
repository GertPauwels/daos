/**
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * GOVERNMENT LICENSE RIGHTS-OPEN SOURCE SOFTWARE
 * The Government's rights to use, modify, reproduce, release, perform, display,
 * or disclose this software are subject to the terms of the LGPL License as
 * provided in Contract No. B609815.
 * Any reproduction of computer software, computer software documentation, or
 * portions thereof marked with this legend must also reproduce the markings.
 *
 * (C) Copyright 2016 Intel Corporation.
 */
/**
 * Object related API and structures
 * Includes:
 * -- VOS object cache API and structures for use by VOS object
 * -- VOS object index structures for internal use by object cache
 * vos/vos_obj.h
 *
 * Author: Vishwanath Venkatesan <vishwanath.venkatesan@intel.com>
 */
#ifndef __VOS_OBJ_H__
#define __VOS_OBJ_H__

#include <daos/btree.h>
#include "vos_layout.h"

#define OT_BTREE_ORDER 20
#define OT_BTREE_CLASS 1008

/**
 * VOS object index
 * in-place btree for object table
 * can have other parameters in future
 * like object statistics associated with
 * it.
 */
struct vos_object_index {
	struct btr_root	obtable;
};

/**
 * Reference of a cached object.
 * NB: DRAM data structure.
 */
struct vos_obj_ref {
	/** TODO: link it to object cache lru and hash table */
	/** Key for searching, container uuid */
	uuid_t				 or_co_uuid;
	/** Key for searching, object ID within a container */
	daos_unit_oid_t			 or_oid;
	/** btree open handle of the object */
	daos_handle_t			 or_toh;
	/** btree iterator handle */
	daos_handle_t			 or_ih;
	/** Persistent memory ID for the object */
	struct vos_obj			*or_obj;
};

/**
 * percpu object cache. It can include a hash table and a LRU for
 * cached objects.
 *
 * This structure is not exported (move to TLS).
 */
struct vos_obj_cache;

/**
 * Find an object in the cache \a occ and take its reference. If the object is
 * not in cache, this function will load it from PMEM pool or create it, then
 * add it to the cache.
 *
 * \param occ	[IN]	Object cache, it could be a percpu data structure.
 * \param coh	[IN]	Container open handle.
 * \param oid	[IN]	VOS object ID.
 * \param oref_p [OUT]	Returned object cache reference.
 */
int
vos_obj_ref_hold(struct vos_obj_cache *occ, daos_handle_t coh,
		 daos_unit_oid_t oid, struct vos_obj_ref **oref_p);

/**
 * Release the object cache reference.
 *
 * \param oref	[IN]	Reference to be released.
 */
void
vos_obj_ref_release(struct vos_obj_cache *occ,
		    struct vos_obj_ref *oref);

/**
 * Create an object cache.
 *
 * \param occ_p	[OUT]	Newly created cache.
 */
int
vos_obj_cache_create(struct vos_obj_cache **occ_p);

/**
 * Destroy an object cache, and release all cached object references.
 *
 * \param occ	[IN]	Cache to be destroyed.
 */
void
vos_obj_cache_destroy(struct vos_obj_cache *occ);

/**
 * Return object cache for the current thread.
 */
struct vos_obj_cache *vos_obj_cache_current(void);

/**
 * Object Index API and handles
 * For internal use by object cache
 */


/**
 * VOS object index update metadata
 * Add a new object ID entry in the object index table
 * Creates an empty tree for the object
 *
 * \param coh	[IN]	Container handle
 * \param oid	[IN]	DAOS object ID
 *			TODO: Additional arguments
 *			to support metadata storage for SR
 *
 * \return		0 on success and negative on
 *			failure
 */
int
vos_oi_update_metadata(daos_handle_t coh, daos_unit_oid_t oid);

/**
 * VOS object index lp
 * Lookup an entry from the OI index
 * If the entry is not found inserts it into the index
 * returns the direct pointer to the VOS object entry
 *
 * \param coh	[IN]	Container handle
 * \param oid	[IN]	DAOS object ID
 * \param obj	[OUT]	Direct pointer to VOS object
 *
 * \return		0 on success and negative on
 *			failure
 */
int
vos_oi_lookup(daos_handle_t coh, daos_unit_oid_t oid,
	      struct vos_obj **obj);
/**
 * VOS object index remove
 * Remove an object ID entry in the object index table
 *
 * \param coh	[IN]	Container handle
 * \param oid	[IN]	DAOS object ID
 *
 * \return		0 on success and negative on
 *			failure
 */
int
vos_oi_remove(daos_handle_t coh, daos_unit_oid_t oid);

#endif
