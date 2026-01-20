from fastapi import APIRouter

router = APIRouter(prefix="/rules", tags=["Rules"])

@router.get("/")
def rules_root():
    return {"message": "Rules API is working"}

