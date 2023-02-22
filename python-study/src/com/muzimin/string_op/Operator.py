str = "member_id"
join_ids = str.split("|")

print(join_ids)

print(' and '.join(map(lambda x: f"a.{x} = b.{x}", join_ids)))